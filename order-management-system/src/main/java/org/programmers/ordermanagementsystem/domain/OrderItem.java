package org.programmers.ordermanagementsystem.domain;

public record OrderItem(Long id, Long orderId, Long itemId, int quantity, String name) {
}