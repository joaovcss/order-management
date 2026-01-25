package com.yonix.order_management.service;

import com.yonix.order_management.entity.Product;
import com.yonix.order_management.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(UUID id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found"));
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public void delete(UUID id){
        if(productRepository.findById(id).isEmpty()){
            throw new RuntimeException("product not found");
        }
        productRepository.deleteById(id);
    }
}
