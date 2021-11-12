package com.payconiq.stockmarket.rest;


import com.payconiq.stockmarket.config.PaginationProperties;
import com.payconiq.stockmarket.dto.RequestStockDTO;
import com.payconiq.stockmarket.dto.ResponseStockDTO;
import com.payconiq.stockmarket.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StockResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockResource.class);

    private final StockService stockService;
    private final PaginationProperties properties;

    public StockResource(StockService stockService, PaginationProperties properties) {
        this.stockService = stockService;
        this.properties = properties;
    }

    @GetMapping("/stocks")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<ResponseStockDTO>> getAllStocks(Pageable pageable) {

        pageable = PageRequest.of(properties.getPageNumber(), properties.getPageSize());

        LOGGER.debug("REST request to get Stocks by page-number: {} and page-size : {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<ResponseStockDTO> page = stockService.getAllStocks(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    @PostMapping("/stocks")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseStockDTO> createStock(@Valid @RequestBody RequestStockDTO stockDTO) {
        LOGGER.debug("REST request to save Stock : {}", stockDTO);
        return ResponseEntity.ok().body(stockService.save(stockDTO));
    }

    @PatchMapping(value = "/stocks/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseStockDTO> partialUpdateAppUser(@PathVariable(value = "id", required = true) final Long id,
                                                                 @NotNull @RequestBody RequestStockDTO stockDT) {
        LOGGER.debug("REST request to partial update Stock partially : {}, {}", id, stockDT);
        return ResponseEntity.ok().body(stockService.partialUpdate(id, stockDT));

    }

    @DeleteMapping("/stocks/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAppUser(@PathVariable Long id) {
        LOGGER.debug("REST request to delete Stock partially : {}", id);
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
