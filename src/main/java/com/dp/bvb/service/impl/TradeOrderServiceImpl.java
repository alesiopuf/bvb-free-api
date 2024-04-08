package com.dp.bvb.service.impl;

import com.dp.bvb.converter.ApplicationConverter;
import com.dp.bvb.dto.StockDTO;
import com.dp.bvb.dto.TradeOrderDTO;
import com.dp.bvb.entity.TradeOrder;
import com.dp.bvb.entity.TradeStatusEnum;
import com.dp.bvb.entity.TradeTypeEnum;
import com.dp.bvb.repository.TradeOrderRepository;
import com.dp.bvb.service.BvbApiService;
import com.dp.bvb.service.StockService;
import com.dp.bvb.service.TradeOrderService;
import com.dp.bvb.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeOrderServiceImpl implements TradeOrderService {

    private final @NonNull TradeOrderRepository tradeOrderRepository;
    private final @NonNull BvbApiService bvbApiService;
    private final @NonNull StockService stockService;
    private final @NonNull WalletService walletService;
    private final @NonNull ApplicationConverter applicationConverter;

    @Override
    public TradeOrderDTO placeOrder(TradeOrderDTO tradeOrderDTO) {
        //validate order
        double price;
        try {
            StockDTO stockDTO = stockService.getStockBySymbol(tradeOrderDTO.getSymbol());
            if (tradeOrderDTO.getQuantity() > stockDTO.getShares() && tradeOrderDTO.getType() == TradeTypeEnum.BUY)
                throw new IllegalArgumentException("Quantity " + tradeOrderDTO.getQuantity()
                        + " is not available to buy for symbol " + tradeOrderDTO.getSymbol());
            if (tradeOrderDTO.getType() == TradeTypeEnum.SELL &&
                    walletService.getWalletForUser(tradeOrderDTO.getUserId()).getPortfolio().get(tradeOrderDTO.getSymbol()) == null)
                throw new IllegalArgumentException("Wallet does not have enough shares to sell for symbol " + tradeOrderDTO.getSymbol());
            if (tradeOrderDTO.getType() == TradeTypeEnum.SELL && tradeOrderDTO.getQuantity() >
                    walletService.getWalletForUser(tradeOrderDTO.getUserId()).getPortfolio().get(tradeOrderDTO.getSymbol()))
                throw new IllegalArgumentException("Wallet does not have enough shares to sell for symbol " + tradeOrderDTO.getSymbol());
            price = bvbApiService.getPriceForSymbol(tradeOrderDTO.getSymbol());
            stockService.updatePriceForSymbol(tradeOrderDTO.getSymbol(), price);
            if (price * tradeOrderDTO.getQuantity() > walletService.getWalletForUser(tradeOrderDTO.getUserId()).getBalance() &&
                    tradeOrderDTO.getType() == TradeTypeEnum.BUY)
                throw new IllegalArgumentException("Insufficient funds to buy " + tradeOrderDTO.getQuantity() + " shares of " + tradeOrderDTO.getSymbol());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to place order: " + e.getMessage());
        }

        //execute order
        TradeOrder tradeOrder = applicationConverter.dtoToEntity(tradeOrderDTO);
        tradeOrder.setStatus(TradeStatusEnum.PENDING);
        tradeOrder.setPrice(price);
        tradeOrder = tradeOrderRepository.save(tradeOrder);
        return applicationConverter.entityToDto(tradeOrder);
    }

    @Override
    public List<TradeOrderDTO> getAllOrders() {
        return tradeOrderRepository.findAll().stream()
                .map(applicationConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TradeOrderDTO> getOrdersForUserAndSymbol(Long userId, String symbol) {
        return tradeOrderRepository.findAll().stream()
                .filter(entity -> userId.equals(entity.getUserId()) && symbol.equals(entity.getSymbol()))
                .map(applicationConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TradeOrderDTO> getOrdersForUser(Long userId) {
        return tradeOrderRepository.findAll().stream()
                .filter(entity -> userId.equals(entity.getUserId()))
                .map(applicationConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Boolean cancelOrder(Long id) {
        Optional<TradeOrder> tradeOrderOptional = tradeOrderRepository.findById(id);
        if (tradeOrderOptional.isEmpty() || tradeOrderOptional.get().getStatus() != TradeStatusEnum.PENDING)
            return false;
        tradeOrderRepository.updateStatus(id, TradeStatusEnum.CANCELED);
        return true;
    }

    @Override
    @Transactional
    public void validateOrders() {
        tradeOrderRepository.findAll().stream()
                .filter(tradeOrder -> tradeOrder.getStatus() == TradeStatusEnum.PENDING)
                .forEach(tradeOrder -> {
                    try {
                        if (tradeOrder.getType() == TradeTypeEnum.BUY) {
                            walletService.updateBalance(tradeOrder.getUserId(), -tradeOrder.getPrice() * tradeOrder.getQuantity());
                            walletService.updatePortfolio(tradeOrder.getUserId(), tradeOrder.getSymbol(), tradeOrder.getQuantity());
                        } else if (tradeOrder.getType() == TradeTypeEnum.SELL) {
                            walletService.updateBalance(tradeOrder.getUserId(), tradeOrder.getPrice() * tradeOrder.getQuantity());
                            walletService.updatePortfolio(tradeOrder.getUserId(), tradeOrder.getSymbol(), -tradeOrder.getQuantity());
                        }
                        tradeOrderRepository.updateStatus(tradeOrder.getId(), TradeStatusEnum.COMPLETED);
                    } catch (Exception e) {
                        tradeOrderRepository.updateStatus(tradeOrder.getId(), TradeStatusEnum.FAILED);
                    }
                });
    }
}