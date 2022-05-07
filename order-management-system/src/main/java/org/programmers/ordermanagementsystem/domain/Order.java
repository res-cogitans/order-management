package org.programmers.ordermanagementsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class Order {
    private Long id;
    private Integer totalPrice;
    private Address address;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private Long memberId;
    private List<OrderItem> orderItems;
}