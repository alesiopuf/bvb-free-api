package com.dp.bvb.service.impl;

import com.dp.bvb.converter.ApplicationConverter;
import com.dp.bvb.dto.StockDTO;
import com.dp.bvb.entity.Stock;
import com.dp.bvb.repository.StockRepository;
import com.dp.bvb.service.StockService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService {

    private final @NonNull StockRepository stockRepository;
    private final @NonNull ApplicationConverter applicationConverter;

    @Override
    public List<StockDTO> getAllStocks() {
        return stockRepository.findAll().stream()
                .map(applicationConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StockDTO getStockBySymbol(String symbol) {
        Optional<Stock> stockOptional = stockRepository.findBySymbol(symbol);
        if (stockOptional.isEmpty())
            throw new EntityNotFoundException("Stock with symbol " + symbol + " does not exist");
        return applicationConverter.entityToDto(stockOptional.get());
    }

    @Override
    @Transactional
    public void updatePriceForSymbol(String symbol, double newPrice) {
        stockRepository.updatePrice(symbol, newPrice);
    }
}
