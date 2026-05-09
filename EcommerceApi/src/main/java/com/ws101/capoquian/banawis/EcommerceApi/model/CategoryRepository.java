package com.ws101.capoquian.banawis.EcommerceApi.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Category entity.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Method Naming: Find category by name
    Optional<Category> findByName(String name);
}
