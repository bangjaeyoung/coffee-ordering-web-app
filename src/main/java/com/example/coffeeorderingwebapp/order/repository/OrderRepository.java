package com.example.coffeeorderingwebapp.order.repository;

import com.example.coffeeorderingwebapp.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
