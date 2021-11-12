package com.payconiq.stockmarket.service;

import com.payconiq.stockmarket.dto.RequestStockDTO;
import com.payconiq.stockmarket.dto.ResponseStockDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockService {

    Page<ResponseStockDTO> getAllStocks(Pageable page);

    ResponseStockDTO save(RequestStockDTO stockDTO);

    ResponseStockDTO partialUpdate(Long id, RequestStockDTO stockDTO);

    void delete(Long id);

    ResponseStockDTO findById(Long id);
}
