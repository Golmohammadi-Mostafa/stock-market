package com.payconiq.stockmarket.service.impl;

import com.payconiq.stockmarket.domain.Stock;
import com.payconiq.stockmarket.dto.RequestStockDTO;
import com.payconiq.stockmarket.dto.ResponseStockDTO;
import com.payconiq.stockmarket.exception.CustomException;
import com.payconiq.stockmarket.repository.StockRepository;
import com.payconiq.stockmarket.service.StockService;
import com.payconiq.stockmarket.service.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public StockServiceImpl(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

/*    @Transactional(readOnly = true)
    @Override
    public Page<ResponseStockDTO> getAllStocks(Pageable page) {
        LOGGER.info("find by  page: {}", page);
        return stockRepository.findAll(page).map(stockMapper::toDto);
    }*/

    @Override
    public ResponseStockDTO save(RequestStockDTO stockDTO) {

        if (stockRepository.existsByName(stockDTO.getName())) {
            throw new CustomException("name is already in use", HttpStatus.NOT_ACCEPTABLE);
        }
        Stock stock = new Stock();
        stock.setCurrentPrice(stockDTO.getCurrentPrice());
        stock.setName(stockDTO.getName());
        return stockMapper.toDto(stockRepository.save(stock));
    }

    @Override
    public ResponseStockDTO partialUpdate(Long id, RequestStockDTO stockDTO) {

        LOGGER.info("Request to partially update Stock : {}", stockDTO);

        Stock stock = stockRepository.findById(id).orElseThrow(() -> new CustomException("Stock does not exist", HttpStatus.NOT_FOUND));

        if (!stockDTO.getName().equals(stock.getName())) {
            throw new CustomException("you can not modified name of stock", HttpStatus.NOT_ACCEPTABLE);
        }

        stock.setCurrentPrice(stockDTO.getCurrentPrice());
        stock.setLastUpdate(new Date());
        return stockMapper.toDto(stockRepository.save(stock));
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Request to delete Stock : {}", id);
        stockRepository.deleteById(id);
    }

    @Override
    public ResponseStockDTO findById(Long id) {
        LOGGER.info("Request to find Stock : {}", id);
        Optional<Stock> byId = stockRepository.findById(id);
        if (byId.isPresent()) return stockMapper.toDto(byId.get());
        else throw new CustomException("Stock not found", HttpStatus.NOT_FOUND);
    }


    @Transactional(readOnly = true)
    @Override
    public Page<ResponseStockDTO> getAllStocks(Pageable page) {
        LOGGER.info("find by  page: {}", page);
        Page<Stock> stockPage = stockRepository.findAll(page);
        List<ResponseStockDTO> dtoList = stockMapper.toDto(stockPage.getContent());
        return new PageImpl<>(dtoList, stockPage.getPageable(), dtoList.size());
    }
}