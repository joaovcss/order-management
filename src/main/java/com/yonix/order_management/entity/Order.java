package com.yonix.order_management.entity;

import com.yonix.order_management.exceptions.OrderExceptions.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal total;

    public static Order create(User user) {
        Order order = new Order();
        order.user = user;
        order.status = OrderStatus.CREATED;
        order.items = new ArrayList<>();
        order.total = BigDecimal.ZERO;
        return order;
    }

    public void addItem(Product product, int quantity) {
        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        product.decreaseStock(quantity);
        OrderItem orderItem = new OrderItem(this, product, quantity, product.getPrice());

        this.items.add(orderItem);

        this.total = this.total.add(orderItem.getTotal());
    }

    public void pay() {
        if(this.status == OrderStatus.CANCELED) {
            throw new OrderCancelledException("Cannot pay this order because the order is already cancelled");
        }
        if(this.status == OrderStatus.PAID) {
            throw new OrderPaidException("Cannot pay this order because the order is already paid");
        }
        if(this.status == OrderStatus.SHIPPED) {
            throw new OrderShippedException("Cannot pay this order because the order is already paid");
        }
        if(this.status == OrderStatus.DELIVERED) {
            throw new OrderDeliveredException("Cannot pay this order because the order is already paid");
        }
        this.status = OrderStatus.PAID;
    }

    public void cancel() {
        if(this.status == OrderStatus.CANCELED) {
            throw new OrderCancelledException("Cannot cancel this order because the order is already cancelled");
        }
        if(this.status == OrderStatus.PAID) {
            throw new OrderPaidException("Cannot cancel this order because the order is already paid");
        }
        if(this.status == OrderStatus.SHIPPED) {
            throw new OrderShippedException("Cannot cancel this order because the order has already been shipped");
        }
        if(this.status == OrderStatus.DELIVERED) {
            throw new OrderDeliveredException("Cannot cancel this order because the order is already delivered");
        }
        for(OrderItem orderItem : this.items) {
            this.getItems().forEach(item -> {
                Product product = item.getProduct();
                Integer quantity = item.getQuantity();
                product.increaseStock(quantity);
            });
        }
        this.status = OrderStatus.CANCELED;
    }

    public void send() {
        if(this.status == OrderStatus.CANCELED) {
            throw new OrderCancelledException("Cannot send an order that already has been cancelled");
        }
        if(this.status == OrderStatus.SHIPPED) {
            throw new OrderShippedException("Cannot send an order that already has been shipped");
        }
        if(this.status == OrderStatus.DELIVERED) {
            throw new OrderDeliveredException("Cannot send an order that already has been delivered");
        }
        this.status = OrderStatus.SHIPPED;
    }

    public void deliver() {
        if(this.status == OrderStatus.DELIVERED){
            throw new OrderCancelledException("Cannot deliver an order that already has been delivered");
        }
        if(this.status == OrderStatus.CANCELED){
            throw new OrderCancelledException("Cannot deliver an order that already has been cancelled");
        }
        if(this.status == OrderStatus.PAID){
            throw new OrderPaidException("Cannot deliver an order that has only been paid");
        }
        this.status = OrderStatus.DELIVERED;
    }
}
