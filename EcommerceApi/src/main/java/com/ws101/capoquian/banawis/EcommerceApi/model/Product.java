package com.ws101.capoquian.banawis.EcommerceApi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Locale.Category;

import org.antlr.v4.runtime.misc.NotNull;

/**
 * Product entity representing items in the e-commerce store.
 * One Category can have many Products - ManyToOne relationship.
 */

    @Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    private Double price;
    
    private String category;
    
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;
    
    private String imageUrl;

    // Default constructor
    public Product() {}

    // Constructor - 
    public Product(String name, String description, Double price, String category, Integer stock, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Object getImageUrl() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getImageUrl'");
    }

    public void setImageUrl(Object imageUrl) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setImageUrl'");
    }
}
