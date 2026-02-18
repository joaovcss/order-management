package com.yonix.order_management.service;

import com.yonix.order_management.dto.mapper.ProductMapper;
import com.yonix.order_management.dto.response.ProductResponse;
import com.yonix.order_management.entity.Product;
import com.yonix.order_management.exceptions.ProductExceptions.ProductNotFoundException;
import com.yonix.order_management.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll(){
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new ProductNotFoundException("there is no product registered");
        }
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (Product product : products) {
            productResponseList.add(ProductMapper.toProductResponse(product));
        }
        return productResponseList;
    }

    public ProductResponse findById(UUID id){
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            throw new ProductNotFoundException("product not found");
        }
        return ProductMapper.toProductResponse(product);
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
