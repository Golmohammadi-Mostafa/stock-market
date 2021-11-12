package com.payconiq.stockmarket.dto;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class RequestStockDTO implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private BigDecimal currentPrice;

    public RequestStockDTO(String name, BigDecimal currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public RequestStockDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
