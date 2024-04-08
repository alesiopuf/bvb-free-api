package com.dp.bvb.dto;

import com.dp.bvb.entity.TradeStatusEnum;
import com.dp.bvb.entity.TradeTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeOrderDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int id;

    private Long userId;

    @NotBlank(message = "Stock symbol is mandatory")
    private String symbol;

    @Min(value = 1, message = "Quantity should be greater than zero")
    private int quantity;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private double price;

    private TradeTypeEnum type;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "EET")
    private Instant createdOn;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private TradeStatusEnum status;

}