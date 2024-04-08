package com.dp.bvb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "trade_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @NotBlank(message = "Stock symbol is mandatory")
    private String symbol;

    @Min(value = 1, message = "Quantity should be greater than zero")
    private int quantity;

    private double price;

    @Enumerated(EnumType.STRING)
    private TradeTypeEnum type;

    @CreationTimestamp
    private Instant createdOn;

    @Enumerated(EnumType.STRING)
    private TradeStatusEnum status;
}