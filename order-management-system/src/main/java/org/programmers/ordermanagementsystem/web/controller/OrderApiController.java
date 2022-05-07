package org.programmers.ordermanagementsystem.web.controller;

import lombok.RequiredArgsConstructor;
import org.programmers.ordermanagementsystem.dto.OrderCreateRequest;
import org.programmers.ordermanagementsystem.dto.OrderDto;
import org.programmers.ordermanagementsystem.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders")
    public OrderDto createOrder(@RequestBody OrderCreateRequest request) {
        return OrderDto.from(orderService.createOrder(request));
    }
}
