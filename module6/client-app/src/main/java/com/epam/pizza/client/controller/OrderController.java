package com.epam.pizza.client.controller;

import com.epam.pizza.client.messaging.OrderProducer;
import com.epam.pizza.client.repository.OrderRepository;
import com.epam.pizza.dto.Order;
import com.epam.pizza.dto.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v01/order")
@Slf4j
@AllArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    @GetMapping
    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order getAllOrders(@PathVariable Long id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        order.setStatus(OrderStatus.NEW);
        orderRepository.save(order);
        orderProducer.sendOrder(order);
        return order;
    }

}
