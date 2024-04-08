package com.dp.bvb.service;

import com.dp.bvb.dto.StockDTO;

import java.util.List;

public interface StockService {

    List<StockDTO> getAllStocks();

    StockDTO getStockBySymbol(String symbol);

    void updatePriceForSymbol(String symbol, double newPrice);

}
