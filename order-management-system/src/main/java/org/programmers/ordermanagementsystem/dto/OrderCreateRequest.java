package org.programmers.ordermanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private Long memberId;
    private List<OrderItemCreateForm> orderItems;
}
