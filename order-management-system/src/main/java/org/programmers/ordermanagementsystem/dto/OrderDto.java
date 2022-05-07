package org.programmers.ordermanagementsystem.dto;

import org.programmers.ordermanagementsystem.domain.*;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(Long id, Integer totalPrice, Address address, OrderStatus orderStatus, LocalDateTime createdAt, Long memberId, List<OrderItem> orderItems) {

    public static OrderDto from(Order order) {
        return new OrderDto(order.getId(), order.getTotalPrice(), order.getAddress(), order.getOrderStatus(), order.getCreatedAt(), order.getMemberId(), order.getOrderItems());
    }
}
