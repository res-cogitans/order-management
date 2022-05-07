package org.programmers.ordermanagementsystem.service;

import org.programmers.ordermanagementsystem.domain.Order;
import org.programmers.ordermanagementsystem.dto.OrderCreateArgs;
import org.programmers.ordermanagementsystem.dto.OrderCreateRequest;

public interface OrderService {

    Order createOrder(OrderCreateRequest request);
}
