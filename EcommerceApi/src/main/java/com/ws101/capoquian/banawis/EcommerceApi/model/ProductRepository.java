package com.ws101.capoquian.banawis.EcommerceApi.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository for Product entity extending JpaRepository.
 * Provides CRUD operations + custom finder methods.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. Method Naming: Find products by category name
    List<Product> findByCategoryName(String categoryName);

    // 2. Method Naming: Find products by name containing keyword
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // 3. @Query with JPQL: Find products within price range
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findProductsByPriceRange(@Param("minPrice") BigDecimal minPrice, 
                                           @Param("maxPrice") BigDecimal maxPrice);

    // 4. Method Naming: Find products with stock greater than
    List<Product> findByStockGreaterThan(Integer stock);
}