package com.dp.bvb.controller;

import com.dp.bvb.dto.StockDTO;
import com.dp.bvb.dto.TradeOrderDTO;
import com.dp.bvb.dto.WalletDTO;
import com.dp.bvb.service.BvbApiService;
import com.dp.bvb.service.StockService;
import com.dp.bvb.service.TradeOrderService;
import com.dp.bvb.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class BvbConnectorController {

    private final TradeOrderService tradeOrderService;
    private final StockService stockService;
    private final WalletService walletService;
    private final BvbApiService bvbApiService;
    private static final String ORDER_CANCELED_MESSAGE_SUCCESS = "Order canceled successfully!";
    private static final String ORDER_CANCELED_MESSAGE_FAIL = "Failed to cancel order!";

    @Autowired
    public BvbConnectorController(TradeOrderService tradeOrderService, BvbApiService bvbApiService,
                                  StockService stockService, WalletService walletService) {
        this.tradeOrderService = tradeOrderService;
        this.bvbApiService = bvbApiService;
        this.stockService = stockService;
        this.walletService = walletService;
    }

    @Operation(description = "Places trade order to BVB stock exchange")
    @PostMapping("/order")
    public ResponseEntity<TradeOrderDTO> placeOrder(@Valid @RequestBody TradeOrderDTO tradeOrderDTO) {
        TradeOrderDTO createdOrder = tradeOrderService.placeOrder(tradeOrderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @Operation(description = "Retrieves all trade orders")
    @GetMapping("/orders")
    public ResponseEntity<List<TradeOrderDTO>> getAllOrders() {
        List<TradeOrderDTO> orders = tradeOrderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Operation(description = "Retrieves all trade orders for a specific user and stock symbol")
    @GetMapping("/orders/{userId}/{symbol}")
    public ResponseEntity<List<TradeOrderDTO>> getOrdersForUserAndSymbol(@Valid @PathVariable Long userId, @Valid @PathVariable String symbol) {
        List<TradeOrderDTO> orders = tradeOrderService.getOrdersForUserAndSymbol(userId, symbol);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Operation(description = "Retrieves all trade orders for a specific userId")
    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<TradeOrderDTO>> getOrdersForUser(@Valid @PathVariable Long userId) {
        List<TradeOrderDTO> orders = tradeOrderService.getOrdersForUser(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Operation(description = "Cancels a trade order to BVB stock exchange")
    @DeleteMapping("/order/{id}")
    public ResponseEntity<String> cancelOrder(@Valid @PathVariable Long id) {
        Boolean isSuccessful = tradeOrderService.cancelOrder(id);
        return Boolean.TRUE.equals(isSuccessful) ? new ResponseEntity<>(ORDER_CANCELED_MESSAGE_SUCCESS, HttpStatus.OK) :
                new ResponseEntity<>(ORDER_CANCELED_MESSAGE_FAIL, HttpStatus.EXPECTATION_FAILED);
    }

    @Operation(description = "Retrieves all stocks")
    @GetMapping("/stocks")
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        List<StockDTO> stocks = stockService.getAllStocks();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @Operation(description = "Create wallet for user")
    @PostMapping("/wallet/{userId}")
    public ResponseEntity<WalletDTO> createWallet(@Valid @PathVariable Long userId) {
        WalletDTO walletDTO = walletService.createWalletForUser(userId);
        return new ResponseEntity<>(walletDTO, HttpStatus.CREATED);
    }

    @Operation(description = "Add funds to user wallet")
    @PostMapping("/wallet/{userId}/funds/{amount}")
    public ResponseEntity<String> addFunds(@Valid @PathVariable Long userId, @Valid @PathVariable Double amount) {
        walletService.updateBalance(userId, amount);
        return new ResponseEntity<>("Funds added successfully!", HttpStatus.OK);
    }

    @Operation(description = "Retrieves user wallet")
    @GetMapping("/wallet/{userId}")
    public ResponseEntity<WalletDTO> getWallet(@Valid @PathVariable Long userId) {
        WalletDTO walletDTO = walletService.getWalletForUser(userId);
        return new ResponseEntity<>(walletDTO, HttpStatus.OK);
    }

    @Operation(description = "Checks stock price at BVB stock exchange")
    @GetMapping("/price/{symbol}")
    public ResponseEntity<Double> getPrice(@Valid @PathVariable String symbol) {
        Double price = bvbApiService.getPriceForSymbol(symbol);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }
}
