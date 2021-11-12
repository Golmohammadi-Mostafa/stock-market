package com.payconiq.stockmarket.service;


import com.payconiq.stockmarket.Application;
import com.payconiq.stockmarket.dto.RequestStockDTO;
import com.payconiq.stockmarket.dto.ResponseStockDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void findAll() {
        // 5 object already inserted to db by sql script
        save();
        PageRequest pageRequest = PageRequest.of(0, 20);
        assertEquals(6, stockService.getAllStocks(pageRequest).getTotalElements());
    }

    @Test
    void partialUpdate() {

        RequestStockDTO stock = new RequestStockDTO();
        stock.setName("TOYOTA");
        BigDecimal bigDecimal = new BigDecimal(80000);
        stock.setCurrentPrice(bigDecimal);

        assertEquals(bigDecimal, stockService.partialUpdate(6L, stock).getCurrentPrice());
    }

    public void save() {
        RequestStockDTO stock = new RequestStockDTO();
        stock.setName("TOYOTA");
        BigDecimal bigDecimal = new BigDecimal(25120);
        stock.setCurrentPrice(bigDecimal);
        ResponseStockDTO save = stockService.save(stock);
        assertEquals(save.getName(), stock.getName());

    }
}
