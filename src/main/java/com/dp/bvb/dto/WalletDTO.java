package com.dp.bvb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private Long userId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private double balance;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Map<String, Integer> portfolio;
}