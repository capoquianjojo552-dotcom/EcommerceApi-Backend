package com.ws101.capoquian.banawis.EcommerceApi.controller;

import com.ws101.capoquian.banawis.EcommerceApi.model.Product;
import com.ws101.capoquian.banawis.EcommerceApi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Sinasabi na REST API controller ito. Auto-convert sa JSON
@RequestMapping("/api/v1/products") // Base path ng lahat ng endpoints dito
public class ProductController {

    private final ProductService productService;

    @Autowired // Auto-inject si ProductService galing kay Spring
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/v1/products - Kunin lahat ng products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // GET /api/v1/products/{id} - Kunin isang product gamit ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET /api/v1/products/filter?filterType=category&filterValue=Electronics
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
                // Simple lang muna: exact price match
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

    // POST /api/v1/products - Gumawa ng bagong product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // PUT /api/v1/products/{id} - Palitan buong product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PATCH /api/v1/products/{id} - Partial update
    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable Long id, @RequestBody Product patch) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // I-update lang yung fields na hindi null
        if (patch.getName() != null) existingProduct.setName(patch.getName());
        if (patch.getDescription() != null) existingProduct.setDescription(patch.getDescription());
        if (patch.getPrice() != null) existingProduct.setPrice(patch.getPrice());
        if (patch.getCategory() != null) existingProduct.setCategory(patch.getCategory());
        if (patch.getStockQuantity() != null) existingProduct.setStockQuantity(patch.getStockQuantity());
        if (patch.getImageUrl() != null) existingProduct.setImageUrl(patch.getImageUrl());
        
        Product updatedProduct = productService.updateProduct(id, existingProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // DELETE /api/v1/products/{id} - Burahin yung product
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
