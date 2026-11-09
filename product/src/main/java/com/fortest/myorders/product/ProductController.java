package com.fortest.myorders.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/products", "/products"})
public record ProductController(ProductService productService) {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    public ResponseEntity<Product>  createProduct(@RequestBody ProductRequest productRequest) {
        log.info("new product registration {}", productRequest);
        Product newProduct = productService.createProduct(productRequest);
        return ResponseEntity.status(201).body(newProduct);
    }

    @GetMapping
    public List<Product> getProduct(){
        log.info("get all products");
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    // Delete a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
