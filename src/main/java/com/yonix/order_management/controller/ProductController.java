package com.yonix.order_management.controller;

import com.yonix.order_management.entity.Product;
import com.yonix.order_management.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService =  productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable UUID id){
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product productData){
        Product product = productService.createProduct(productData);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
