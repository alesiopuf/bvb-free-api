package com.dp.bvb.runner;

import com.dp.bvb.entity.Stock;
import com.dp.bvb.repository.StockRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockBaselineLoader {

    @Value("${baseline-stocks-path}")
    private String baselineStocksPath;

    private final @NonNull StockRepository stockRepository;

    private void readStocksFromFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(baselineStocksPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Stock stock = parseStock(line);
                if (stock != null) {
                    stockRepository.save(stock);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stock parseStock(String line) {
        String[] parts = line.split(";");
        if (parts.length != 13) { // Ensure correct number of fields in each line
            log.error("Invalid line: " + line);
            return null;
        }

        try {
            return new Stock(
                    null, // Set id to null or initialize appropriately
                    parts[0], // Symbol
                    parts[1], // Name
                    parts[2], // ISIN
                    parts[3], // CAEN Code
                    parts[4], // Tax Code
                    parts[5], // County
                    parts[6], // Country
                    parts[7], // Exchange Section
                    parts[8], // Main Market
                    parts[9], // Category
                    parts[10], // Status
                    Integer.parseInt(parts[11]), // Shares
                    Double.parseDouble(parts[12].replace(",", ".")) // Nominal Value (replace comma with period)
            );
        } catch (NumberFormatException e) {
            log.error("Error parsing line: " + line);
            e.printStackTrace();
            return null;
        }
    }

    @EventListener(ApplicationStartedEvent.class)
    public void run() {
//        readStocksFromFile();
    }
}
