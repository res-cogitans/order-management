package org.programmers.ordermanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.programmers.ordermanagementsystem.domain.Address;
import org.programmers.ordermanagementsystem.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateArgs {
    private Integer totalPrice;
    private Address address;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private Long memberId;
    private List<OrderItemCreateForm> orderItems;
}