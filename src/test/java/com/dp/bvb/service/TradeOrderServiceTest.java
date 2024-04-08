package com.dp.bvb.service;

import com.dp.bvb.TestUtil;
import com.dp.bvb.converter.ApplicationConverter;
import com.dp.bvb.dto.StockDTO;
import com.dp.bvb.dto.TradeOrderDTO;
import com.dp.bvb.dto.WalletDTO;
import com.dp.bvb.entity.TradeOrder;
import com.dp.bvb.repository.TradeOrderRepository;
import com.dp.bvb.service.impl.TradeOrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TradeOrderServiceTest {

    @Mock
    private TradeOrderRepository tradeOrderRepository;

    @Mock
    private BvbApiService bvbApiService;

    @Mock
    private StockService stockService;

    @Mock
    private WalletService walletService;

    @Mock
    private ApplicationConverter applicationConverter;

    @InjectMocks
    private TradeOrderServiceImpl tradeOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder() {
        TradeOrderDTO orderDTO = TestUtil.getTradeOrderDTO1();
        TradeOrder order = TestUtil.getTradeOrder1();
        StockDTO stockDTO = mock(StockDTO.class);
        WalletDTO walletDTO = mock(WalletDTO.class);
        when(applicationConverter.dtoToEntity(orderDTO)).thenReturn(order);
        when(tradeOrderRepository.save(order)).thenReturn(order);
        when(applicationConverter.entityToDto(order)).thenReturn(orderDTO);
        when(stockService.getStockBySymbol("AAPL")).thenReturn(stockDTO);
        when(walletService.getWalletForUser(1L)).thenReturn(walletDTO);
        when(walletDTO.getPortfolio()).thenReturn(Map.of("AAPL", 100));
        when(stockDTO.getShares()).thenReturn(1000);

        TradeOrderDTO result = tradeOrderService.placeOrder(orderDTO);

        assertEquals(orderDTO, result);
        verify(applicationConverter).dtoToEntity(orderDTO);
        verify(tradeOrderRepository).save(order);
        verify(applicationConverter).entityToDto(order);
    }

    @Test
    void testGetAllOrders() {
        TradeOrder order1 = TestUtil.getTradeOrder1();
        TradeOrder order2 = TestUtil.getTradeOrder2();
        List<TradeOrder> orders = Arrays.asList(order1, order2);
        when(tradeOrderRepository.findAll()).thenReturn(orders);

        TradeOrderDTO orderDTO1 = TestUtil.getTradeOrderDTO1();
        TradeOrderDTO orderDTO2 = TestUtil.getTradeOrderDTO2();
        when(applicationConverter.entityToDto(order1)).thenReturn(orderDTO1);
        when(applicationConverter.entityToDto(order2)).thenReturn(orderDTO2);

        List<TradeOrderDTO> result = tradeOrderService.getAllOrders();

        assertEquals(2, result.size());
        assertEquals(orderDTO1, result.get(0));
        assertEquals(orderDTO2, result.get(1));
        verify(tradeOrderRepository).findAll();
        verify(applicationConverter).entityToDto(order1);
        verify(applicationConverter).entityToDto(order2);
    }
}