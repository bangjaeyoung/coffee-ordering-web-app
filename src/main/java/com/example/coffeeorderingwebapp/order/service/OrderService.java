package com.example.coffeeorderingwebapp.order.service;

import com.example.coffeeorderingwebapp.order.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    public Order createOrder(Order order) {

        // TODO order 객체는 나중에 DB에 저장 후, 되돌려 받는 것으로 변경 필요
        return order;
    }

    public Order findOrder(long orderId) {

        // TODO order 객체는 나중에 DB에서 조회 하는 것으로 변경 필요.
        return new Order(1L, 1L);
    }

    public List<Order> findOrders() {

        // TODO order 객체는 나중에 DB에서 조회하는 것으로 변경 필요.
        return List.of(
                new Order(1L, 1L),
                new Order(2L, 2L)
        );
    }

    public void cancelOrder() {
        // TODO
    }
}
