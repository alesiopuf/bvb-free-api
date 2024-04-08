package com.dp.bvb.controller;

import com.dp.bvb.dto.StockDTO;
import com.dp.bvb.dto.TradeOrderDTO;
import com.dp.bvb.service.BvbApiService;
import com.dp.bvb.service.StockService;
import com.dp.bvb.service.TradeOrderService;
import com.dp.bvb.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(BvbConnectorController.class)
class BvbConnectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeOrderService tradeOrderService;

    @MockBean
    private BvbApiService bvbApiService;

    @MockBean
    private StockService stockService;

    @MockBean
    private WalletService walletService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPlaceOrder() throws Exception {
        TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
        when(tradeOrderService.placeOrder(any(TradeOrderDTO.class))).thenReturn(tradeOrderDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void testGetAllOrders() throws Exception {
        TradeOrderDTO tradeOrderDTO1 = new TradeOrderDTO();
        TradeOrderDTO tradeOrderDTO2 = new TradeOrderDTO();
        List<TradeOrderDTO> tradeOrderDTOList = Arrays.asList(tradeOrderDTO1, tradeOrderDTO2);
        when(tradeOrderService.getAllOrders()).thenReturn(tradeOrderDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCancelOrder_Success() throws Exception {
        Long orderId = 1L;
        when(tradeOrderService.cancelOrder(orderId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/order/{id}", orderId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCancelOrder_Fail() throws Exception {
        Long orderId = 2L;
        when(tradeOrderService.cancelOrder(orderId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/order/{id}", orderId))
                .andExpect(MockMvcResultMatchers.status().isExpectationFailed());
    }

    @Test
    void testGetAllStocks() throws Exception {
        StockDTO stockDTO1 = new StockDTO();
        StockDTO stockDTO2 = new StockDTO();
        List<StockDTO> stockDTOList = Arrays.asList(stockDTO1, stockDTO2);
        when(stockService.getAllStocks()).thenReturn(stockDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/stocks"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetPrice() throws Exception {
        String symbol = "AAPL";
        Double price = 150.0;
        when(bvbApiService.getPriceForSymbol(symbol)).thenReturn(price);

        mockMvc.perform(MockMvcRequestBuilders.get("/price/{symbol}", symbol))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(price.toString()));
    }
}