package com.yonix.order_management.entity;

import com.yonix.order_management.dto.request.CreateProductRequest;
import com.yonix.order_management.dto.request.UpdateProductRequest;
import com.yonix.order_management.exceptions.ProductExceptions.InsufficientStockException;
import com.yonix.order_management.exceptions.ProductExceptions.UpdateProductException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    private Integer stock;

    public static Product create(CreateProductRequest request) {
        Product product = new Product();
        product.name = request.name();
        product.description = request.description();
        product.price = request.price();
        product.stock = request.stock();
        return product;
    }

    public static Product update(Product product,UpdateProductRequest request) {
        if(request == null){
            throw new UpdateProductException("the request cannot be null");
        }
        if(product == null) {
            throw new UpdateProductException("the product to update cannot be null");
        }
        if(request.name() != null){
            product.name = request.name();
        }
        if(request.description() != null){
            product.description = request.description();
        }
        if(request.price() != null){
            product.price = request.price();
        }
        if(request.stock() != null){
            product.stock = request.stock();
        }
        return product;
    }

    public void decreaseStock(int quantity) {
        if(this.stock < quantity) {
            throw new InsufficientStockException();
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.stock += quantity;
    }
}
