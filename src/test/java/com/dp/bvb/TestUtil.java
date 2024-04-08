package com.dp.bvb;

import com.dp.bvb.dto.TradeOrderDTO;
import com.dp.bvb.entity.TradeOrder;
import com.dp.bvb.entity.TradeStatusEnum;
import com.dp.bvb.entity.TradeTypeEnum;

import java.time.Instant;

public class TestUtil {

    public static TradeOrderDTO getTradeOrderDTO1() {
        TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
        tradeOrderDTO.setId(1);
        tradeOrderDTO.setUserId(1L);
        tradeOrderDTO.setSymbol("AAPL");
        tradeOrderDTO.setQuantity(10);
        tradeOrderDTO.setPrice(150.0);
        tradeOrderDTO.setCreatedOn(Instant.now());
        tradeOrderDTO.setStatus(TradeStatusEnum.PENDING);
        tradeOrderDTO.setType(TradeTypeEnum.BUY);
        return tradeOrderDTO;
    }

    public static TradeOrderDTO getTradeOrderDTO2() {
        TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
        tradeOrderDTO.setId(2);
        tradeOrderDTO.setUserId(101L);
        tradeOrderDTO.setSymbol("GOOGL");
        tradeOrderDTO.setQuantity(5);
        tradeOrderDTO.setPrice(2500.0);
        tradeOrderDTO.setCreatedOn(Instant.now());
        tradeOrderDTO.setStatus(TradeStatusEnum.COMPLETED);
        tradeOrderDTO.setType(TradeTypeEnum.SELL);
        return tradeOrderDTO;
    }

    public static TradeOrder getTradeOrder1() {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setId(1L);
        tradeOrder.setUserId(1L);
        tradeOrder.setSymbol("AAPL");
        tradeOrder.setQuantity(10);
        tradeOrder.setPrice(150.0);
        tradeOrder.setCreatedOn(Instant.now());
        tradeOrder.setStatus(TradeStatusEnum.PENDING);
        tradeOrder.setType(TradeTypeEnum.BUY);
        return tradeOrder;
    }

    public static TradeOrder getTradeOrder2() {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setId(2L);
        tradeOrder.setUserId(101L);
        tradeOrder.setSymbol("GOOGL");
        tradeOrder.setQuantity(5);
        tradeOrder.setPrice(2500.0);
        tradeOrder.setCreatedOn(Instant.now());
        tradeOrder.setStatus(TradeStatusEnum.COMPLETED);
        tradeOrder.setType(TradeTypeEnum.SELL);
        return tradeOrder;
    }
}
