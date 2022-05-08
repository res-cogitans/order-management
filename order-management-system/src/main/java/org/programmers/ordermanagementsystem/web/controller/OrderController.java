package org.programmers.ordermanagementsystem.web.controller;

import lombok.RequiredArgsConstructor;
import org.programmers.ordermanagementsystem.dto.MemberCreateForm;
import org.programmers.ordermanagementsystem.dto.MemberDto;
import org.programmers.ordermanagementsystem.dto.OrderDto;
import org.programmers.ordermanagementsystem.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public String memberList(Model model) {
        List<OrderDto> orders = orderService.getAllOrders().stream().map(OrderDto::from).toList();
        model.addAttribute("orders", orders);

        return "/order/orderList";
    }

    @PostMapping("/orders/{id}/complete")
    public String completeOrder(@PathVariable Long id) {
        orderService.completeOrder(id);

        return "redirect:/orders";
    }

    @PostMapping("/orders/{id}/cancel")
    public String cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);

        return "redirect:/orders";
    }
}
