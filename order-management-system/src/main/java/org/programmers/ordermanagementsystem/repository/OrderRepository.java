package org.programmers.ordermanagementsystem.repository;

import org.programmers.ordermanagementsystem.domain.Order;
import org.programmers.ordermanagementsystem.domain.OrderStatus;
import org.programmers.ordermanagementsystem.dto.OrderCreateArgs;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(OrderCreateArgs args);

    void updateOrderStatus(Long id, OrderStatus orderStatus);

    Optional<Order> findById(Long id);

    List<Order> findAll();
}
