package com.dp.bvb.service;

import com.dp.bvb.dto.TradeOrderDTO;

import java.util.List;

public interface TradeOrderService {

    TradeOrderDTO placeOrder(TradeOrderDTO tradeOrderDTO);

    List<TradeOrderDTO> getAllOrders();

    List<TradeOrderDTO> getOrdersForUserAndSymbol(Long userId, String symbol);

    List<TradeOrderDTO> getOrdersForUser(Long userId);

    Boolean cancelOrder(Long id);

    void validateOrders();
}
