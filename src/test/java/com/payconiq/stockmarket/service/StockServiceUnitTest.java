package com.payconiq.stockmarket.service;


import com.payconiq.stockmarket.domain.Stock;
import com.payconiq.stockmarket.dto.ResponseStockDTO;
import com.payconiq.stockmarket.repository.StockRepository;
import com.payconiq.stockmarket.service.impl.StockServiceImpl;
import com.payconiq.stockmarket.service.mapper.StockMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceUnitTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockMapper stockMapper;

//    @BeforeEach
//    void setup() {
//        stockRepository = Mockito.mock(StockRepository.class);
//        stockMapper = Mockito.mock(StockMapper.class);
//        stockService = new StockServiceImpl(stockRepository, stockMapper);
//    }

    @Test
    void searchById_findById_shouldReturnResource() {
        ResponseStockDTO dto1 = new ResponseStockDTO(1L, "APPLE", new BigDecimal(2000), null);
        Stock stock1 = new Stock();
        stock1.setId(dto1.getId());
        stock1.setName(dto1.getName());
        stock1.setCurrentPrice(dto1.getCurrentPrice());
        stock1.setLastUpdate(dto1.getLastUpdate());

        Optional<Stock> stock = Optional.of(stock1);
        when(stockRepository.findById(dto1.getId())).thenReturn(stock);
        when(stockMapper.toDto(stock1)).thenReturn(dto1);
        ResponseStockDTO responseStockDTO = stockService.findById(dto1.getId());
        assertEquals(1L, responseStockDTO.getId());

    }

    @Test
    void search_findById_shouldReturnResource() {
        // given
        Stock mockEntity = Mockito.mock(Stock.class);
        Optional<Stock> optionalStock = Optional.of(mockEntity);
        ResponseStockDTO mockDto = Mockito.mock(ResponseStockDTO.class);

        when(stockRepository.findById(1L)).thenReturn(optionalStock);
        when(stockMapper.toDto(mockEntity)).thenReturn(mockDto);

        // when
        ResponseStockDTO resource = stockService.findById(1L);

        // then

        assertEquals(resource, mockDto);
    }

    @Test
    void findAll_getAllStocks_shouldReturnAllResources() {
        // given
        List<Stock> stocks = new ArrayList<>();
        List<ResponseStockDTO> stockDTOS = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 20);

        Stock stock1 = new Stock();
        Stock stock2 = new Stock();
        stock1.setId(1L);
        stock1.setName("APPLE");
        stock1.setCurrentPrice(new BigDecimal(2500));
        stock1.setLastUpdate(null);

        stock2.setId(2L);
        stock2.setName("SONY");
        stock2.setCurrentPrice(new BigDecimal(3500));
        stock2.setLastUpdate(null);

        stocks.add(stock1);
        stocks.add(stock2);

        ResponseStockDTO stockDTO1 = new ResponseStockDTO(stock1.getId(), stock1.getName(), stock1.getCurrentPrice(), stock1.getLastUpdate());
        ResponseStockDTO stockDTO2 = new ResponseStockDTO(stock2.getId(), stock2.getName(), stock2.getCurrentPrice(), stock2.getLastUpdate());

        stockDTOS.add(stockDTO1);
        stockDTOS.add(stockDTO2);

        Page<Stock> all = new PageImpl<>(stocks, pageRequest, stocks.size());

        when(stockRepository.findAll(pageRequest)).thenReturn(all);
        when(stockMapper.toDto(stocks)).thenReturn(stockDTOS);

        Page<ResponseStockDTO> pageDto = stockService.getAllStocks(pageRequest);
        assertEquals(pageDto.getContent().size(), all.getTotalElements());
    }
}


//[UnitOfWork_StateUnderTest_ExpectedBehavior]








