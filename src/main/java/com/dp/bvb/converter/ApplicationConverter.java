package com.dp.bvb.converter;

import com.dp.bvb.dto.StockDTO;
import com.dp.bvb.dto.TradeOrderDTO;
import com.dp.bvb.dto.WalletDTO;
import com.dp.bvb.entity.Stock;
import com.dp.bvb.entity.TradeOrder;
import com.dp.bvb.entity.Wallet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationConverter {

    private final @NonNull ModelMapper modelMapper;

    public TradeOrderDTO entityToDto(TradeOrder tradeOrder) {
        return modelMapper.map(tradeOrder, TradeOrderDTO.class);
    }

    public TradeOrder dtoToEntity(TradeOrderDTO tradeOrderDTO) {
        return modelMapper.map(tradeOrderDTO, TradeOrder.class);
    }

    public StockDTO entityToDto(Stock stock) {
        return modelMapper.map(stock, StockDTO.class);
    }

    public Stock dtoToEntity(StockDTO stockDTO) {
        return modelMapper.map(stockDTO, Stock.class);
    }

    public WalletDTO entityToDto(com.dp.bvb.entity.Wallet wallet) {
        return modelMapper.map(wallet, WalletDTO.class);
    }

    public Wallet dtoToEntity(WalletDTO walletDTO) {
        return modelMapper.map(walletDTO, Wallet.class);
    }
}
