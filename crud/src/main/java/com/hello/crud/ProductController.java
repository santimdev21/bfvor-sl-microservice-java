package com.hello.crud;
import com.hello.crud.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
   
    private final List<Product> products; 

    public ProductController() {
        products = new ArrayList<>();
        
        // Initialize with sample data
        Product notebook = new Product();
        notebook.setId(143);
        notebook.setName("Notebook");
        notebook.setPrice(5.49);
        
        Product marker = new Product();
        marker.setId(144);
        marker.setName("Black Marker");
        marker.setPrice(1.99);
        
        products.add(notebook);
        products.add(marker);
    }

    // All Rest Endpoint Goes Here
    // GET /products
    @GetMapping
    public List<Product> getProducts() {
        return products;
    }
    // GET /products/{id}
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
    // POST /products
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody Product product) {
        products.add(product);
    }
    // PUT /products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        Optional<Product> existingProduct = products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            if(updatedProduct.getName() != null)
                product.setName(updatedProduct.getName());
            if(updatedProduct.getPrice() != null)
                product.setPrice(updatedProduct.getPrice());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    // DELETE /products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (product.isPresent()) {
            products.remove(product.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}


