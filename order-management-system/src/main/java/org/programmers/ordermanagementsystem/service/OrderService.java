package org.programmers.ordermanagementsystem.service;

import org.programmers.ordermanagementsystem.domain.Order;
import org.programmers.ordermanagementsystem.dto.OrderCreateRequest;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderCreateRequest request);

    List<Order> getAllOrders();

    void completeOrder(Long id);

    void cancelOrder(Long id);
}
