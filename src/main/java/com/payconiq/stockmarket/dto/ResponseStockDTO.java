package com.payconiq.stockmarket.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ResponseStockDTO implements Serializable {

    private Long id;

    private String name;

    private BigDecimal currentPrice;

    private Date lastUpdate;

    public ResponseStockDTO(Long id, String name, BigDecimal currentPrice, Date lastUpdate) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "StockDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentPrice=" + currentPrice +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
