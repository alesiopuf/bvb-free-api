package com.dp.bvb.dto;

import lombok.Data;

@Data
public class StockDTO {
    private Long id;
    private String symbol;
    private String name;
    private String isin;
    private String caenCode;
    private String taxCode;
    private String county;
    private String country;
    private String exchangeSection;
    private String mainMarket;
    private String category;
    private String status;
    private int shares;
    private double nominalValue;
}
