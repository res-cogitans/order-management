package org.programmers.ordermanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.programmers.ordermanagementsystem.domain.*;
import org.programmers.ordermanagementsystem.dto.ItemDto;
import org.programmers.ordermanagementsystem.dto.OrderCreateArgs;
import org.programmers.ordermanagementsystem.dto.OrderCreateRequest;
import org.programmers.ordermanagementsystem.dto.OrderItemCreateForm;
import org.programmers.ordermanagementsystem.exception.ProhibitedItemException;
import org.programmers.ordermanagementsystem.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final ItemService itemService;

    @Override
    @Transactional
    public Order createOrder(OrderCreateRequest request) {
        Member orderedMember = memberService.getMemberById(request.getMemberId());
        boolean isMemberMinorAge = orderedMember.getAge().isMinorAge();
        List<OrderItemCreateForm> orderItems = request.getOrderItems();
        int totalPrice = 0;
        for (OrderItemCreateForm orderItem : orderItems) {
            var item = itemService.getItemById(orderItem.itemId());
            if (item.getType().isProhibitedForMinor() && isMemberMinorAge) {
                throw new ProhibitedItemException(Age.notMinorAgeBirthYear()+ "년생 이상만 구입 가능한 품목입니다: " + item.getName());
            }
            Item itemAfterOrder = item.order(orderItem.quantity());
            totalPrice += item.getPrice() * orderItem.quantity();
            itemService.updateItem(ItemDto.from(itemAfterOrder));
        }

        return orderRepository.save(new OrderCreateArgs(totalPrice, orderedMember.getAddress(), OrderStatus.ACCEPTED,
                LocalDateTime.now(), orderedMember.getId(), request.getOrderItems()));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void completeOrder(Long id) {
        orderRepository.updateOrderStatus(id, OrderStatus.COMPLETE);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.updateOrderStatus(id, OrderStatus.CANCELED);
    }
}
