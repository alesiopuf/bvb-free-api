package com.dp.bvb.repository;

import com.dp.bvb.entity.TradeOrder;
import com.dp.bvb.entity.TradeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {

    @Modifying
    @Query("update TradeOrder t set t.status = :status where t.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") TradeStatusEnum status);

}
