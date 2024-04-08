package com.dp.bvb.repository;

import com.dp.bvb.entity.Stock;
import com.dp.bvb.entity.TradeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findBySymbol(String symbol);

    @Modifying
    @Query("update Stock s set s.nominalValue = :nominalValue where s.symbol = :symbol")
    void updatePrice(@Param("symbol") String symbol, @Param("nominalValue") double nominalValue);
}
