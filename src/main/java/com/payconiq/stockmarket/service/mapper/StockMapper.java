package com.payconiq.stockmarket.service.mapper;

import com.payconiq.stockmarket.domain.Stock;
import com.payconiq.stockmarket.dto.ResponseStockDTO;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Stock} and its DTO {@link ResponseStockDTO}.
 */
@Mapper(componentModel = "spring")
public interface StockMapper extends EntityMapper<ResponseStockDTO, Stock> {

    ResponseStockDTO toDto(Stock s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponseStockDTO toDtoId(Stock stock);
}
