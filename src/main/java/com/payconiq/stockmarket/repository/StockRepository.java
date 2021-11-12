package com.payconiq.stockmarket.repository;


import com.payconiq.stockmarket.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    boolean existsByName(String name);
}
