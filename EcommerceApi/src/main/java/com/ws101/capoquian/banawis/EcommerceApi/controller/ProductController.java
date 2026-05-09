package com.ws101.capoquian.banawis.EcommerceApi.controller;

import com.ws101.capoquian.banawis.EcommerceApi.model.Product;
import com.ws101.capoquian.banawis.EcommerceApi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/v1/products") 
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam String filterType,
            @RequestParam String filterValue) {
        
        List<Product> filteredProducts;
        
        switch (filterType.toLowerCase()) {
            case "category":
                filteredProducts = productService.getProductsByCategory(filterValue);
                break;
            case "name":
                filteredProducts = productService.searchProductsByName(filterValue);
                break;
            case "price":
                
                double price = Double.parseDouble(filterValue);
                filteredProducts = productService.getAllProducts().stream()
                        .filter(p -> p.getPrice().equals(price))
                        .toList();
                break;
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(filteredProducts, HttpStatus.OK);
    }

    
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable Long id, @RequestBody Product patch) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        
        if (patch.getName() != null) existingProduct.setName(patch.getName());
        if (patch.getDescription() != null) existingProduct.setDescription(patch.getDescription());
        if (patch.getPrice() != null) existingProduct.setPrice(patch.getPrice());
        if (patch.getCategory() != null) existingProduct.setCategory(patch.getCategory());
        if (patch.getStockQuantity() != null) existingProduct.setStockQuantity(patch.getStockQuantity());
        if (patch.getImageUrl() != null) existingProduct.setImageUrl(patch.getImageUrl());
        
        Product updatedProduct = productService.updateProduct(id, existingProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
