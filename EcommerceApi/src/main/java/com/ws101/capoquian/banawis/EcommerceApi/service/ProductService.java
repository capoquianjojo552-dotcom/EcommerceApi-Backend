package com.ws101.capoquian.banawis.EcommerceApi.service;

import com.ws101.capoquian.banawis.EcommerceApi.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service // Sinasabi kay Spring na ito ay Service class
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private Long nextId = 1L; // Para sa auto-increment na ID

    // Constructor - dito natin lagay yung 10 sample products
    public ProductService() {
        products.add(new Product(nextId++, "Laptop", "Gaming Laptop 16GB RAM", 75000.00, "Electronics", 10, "https://example.com/laptop.jpg"));
        products.add(new Product(nextId++, "Mouse", "Wireless Gaming Mouse", 1500.00, "Electronics", 25, "https://example.com/mouse.jpg"));
        products.add(new Product(nextId++, "Keyboard", "Mechanical RGB Keyboard", 3500.00, "Electronics", 15, "https://example.com/keyboard.jpg"));
        products.add(new Product(nextId++, "T-Shirt", "Cotton Black T-Shirt", 499.00, "Clothing", 50, "https://example.com/tshirt.jpg"));
        products.add(new Product(nextId++, "Jeans", "Denim Blue Jeans", 1299.00, "Clothing", 30, "https://example.com/jeans.jpg"));
        products.add(new Product(nextId++, "Sneakers", "Running Shoes", 2499.00, "Footwear", 20, "https://example.com/shoes.jpg"));
        products.add(new Product(nextId++, "Backpack", "Laptop Backpack 15 inch", 999.00, "Accessories", 40, "https://example.com/bag.jpg"));
        products.add(new Product(nextId++, "Water Bottle", "Insulated Steel Bottle 1L", 650.00, "Accessories", 100, "https://example.com/bottle.jpg"));
        products.add(new Product(nextId++, "Headphones", "Noise Cancelling Headphones", 8900.00, "Electronics", 12, "https://example.com/headphones.jpg"));
        products.add(new Product(nextId++, "Monitor", "27-inch 144Hz Monitor", 12500.00, "Electronics", 8, "https://example.com/monitor.jpg"));
    }

    // 1. Kunin lahat ng products
    public List<Product> getAllProducts() {
        return new ArrayList<>(products); // Return copy para hindi ma-modify yung original list
    }

    // 2. Hanapin yung product gamit ID
    public Product getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null); // Return null pag wala
    }

    // 3. Gumawa ng bagong product
    public Product createProduct(Product product) {
        product.setId(nextId++); // Auto-set yung ID
        products.add(product);
        return product;
    }

    // 4. I-update yung existing product
    public Product updateProduct(Long id, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                updatedProduct.setId(id); // Siguraduhin same ID
                products.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null; // Pag walang nahanap
    }

    // 5. I-delete yung product
    public boolean deleteProduct(Long id) {
        return products.removeIf(product -> product.getId().equals(id));
    }

    // 6. Filter by category
    public List<Product> getProductsByCategory(String category) {
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // 6. Filter by name - partial match
    public List<Product> searchProductsByName(String name) {
        return products.stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}
