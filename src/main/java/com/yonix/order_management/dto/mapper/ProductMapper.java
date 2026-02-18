package com.yonix.order_management.dto.mapper;

import com.yonix.order_management.dto.response.ProductResponse;
import com.yonix.order_management.entity.Product;

public class ProductMapper {
    public static ProductResponse toProductResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
