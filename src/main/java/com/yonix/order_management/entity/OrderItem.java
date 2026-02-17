package com.yonix.order_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItem {

    public OrderItem(Order order, Product product, Integer quantity, BigDecimal unitPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    private BigDecimal unitPrice;

    public static OrderItem create(Order order, Product product, Integer quantity, BigDecimal unitPrice) {
        return new OrderItem(order, product, quantity, unitPrice);
    }

    public BigDecimal getTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
