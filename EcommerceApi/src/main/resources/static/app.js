// Base URL ng Spring Boot API mo
const API_BASE_URL = 'http://localhost:8080/api';

/**
 * 1. Utility function: fetchProducts() using async/await
 * Fetches all products from the database via REST API
 */
async function fetchProducts() {
    try {
        console.log('Fetching products from database...');
        
        const response = await fetch(${API_BASE_URL}/products);
        
        // 2. Error Handling: Check response.ok manually
        if (!response.ok) {
            if (response.status === 404) {
                throw new Error('Products not found - 404');
            } else if (response.status === 500) {
                throw new Error('Server error - 500');
            } else {
                throw new Error(HTTP Error: ${response.status});
            }
        }
        
        const products = await response.json();
        console.log('Products fetched successfully:', products);
        return products;
        
    } catch (error) {
        // Log specific error messages to console for debugging
        console.error('Error fetching products:', error.message);
        throw error; // Re-throw para ma-catch sa render function
    }
}

/**
 * 3. Dynamic Rendering: Inject HTML into <main> tag
 */
async function renderProducts() {
    const mainTag = document.querySelector('main');
    
    try {
        // Show loading state
        mainTag.innerHTML = '<p class="loading">Loading products...</p>';
        
        const products = await fetchProducts();
        
        // Handle "Empty State" if API returns empty list
        if (!products || products.length === 0) {
            mainTag.innerHTML = `
                <div class="empty-state">
                    <h2>No Products Available</h2>
                    <p>There are no products in the database yet.</p>
                </div>
            `;
            return;
        }
        
        // Dynamically inject HTML for product grid
        const productGridHTML = products.map(product => `
            <div class="product-card" data-id="${product.id}">
                <h3>${product.name}</h3>
                <p class="description">${product.description || 'No description'}</p>
                <p class="price">₱${product.price}</p>
                <p class="stock">Stock: ${product.stock}</p>
                <p class="category">Category: ${product.category ? product.category.name : 'Uncategorized'}</p>
                <button onclick="deleteProduct(${product.id})">Delete</button>
                <button onclick="editProduct(${product.id})">Edit</button>
            </div>
        `).join('');
        
        mainTag.innerHTML = `
            <div class="product-grid">
                ${productGridHTML}
            </div>
        `;
        
    } catch (error) {
        // Show error state sa UI
        mainTag.innerHTML = `
            <div class="error-state">
                <h2>Failed to Load Products</h2>
                <p>${error.message}</p>
                <button onclick="renderProducts()">Retry</button>
            </div>
        `;
    }
}

/**
 * POST: Create new product
 */
async function createProduct(productData) {
    try {
        const response = await fetch(${API_BASE_URL}/products, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productData)
        });
        
        if (!response.ok) {
            throw new Error(Failed to create product: ${response.status});
        }
        
        const newProduct = await response.json();
        console.log('Product created:', newProduct);
        
        // Refresh mo yung list after create
        await renderProducts();
        return newProduct;
        
    } catch (error) {
        console.error('Error creating product:', error.message);
        alert('Failed to create product: ' + error.message);
    }
}

/**
 * DELETE: Delete product by ID
 */
async function deleteProduct(id) {
    if (!confirm('Are you sure you want to delete this product?')) return;
    
    try {
        const response = await fetch(${API_BASE_URL}/products/${id}, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error(Failed to delete product: ${response.status});
        }
        
        console.log('Product deleted:', id);
        await renderProducts(); // Refresh list
        
    } catch (error) {
        console.error('Error deleting product:', error.message);
        alert('Failed to delete product: ' + error.message);
    }
}

// 3. Call fetchProducts() on page load
document.addEventListener('DOMContentLoaded', () => {
    console.log('Page loaded. Fetching products...');
    renderProducts();
});