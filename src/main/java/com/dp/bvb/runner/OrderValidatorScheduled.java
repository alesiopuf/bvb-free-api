package com.dp.bvb.runner;

import com.dp.bvb.service.TradeOrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderValidatorScheduled {

    private final @NonNull TradeOrderService tradeOrderService;

    @Scheduled(cron = "${order-validation-cron}")
    private void validateOrders() {
//        log.info("Validating trade orders.");
        tradeOrderService.validateOrders();
    }
}
