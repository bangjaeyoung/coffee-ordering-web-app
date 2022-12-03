package com.example.coffeeorderingwebapp.order.controller;

import com.example.coffeeorderingwebapp.order.dto.OrderPostDto;
import com.example.coffeeorderingwebapp.order.dto.OrderResponseDto;
import com.example.coffeeorderingwebapp.order.entity.Order;
import com.example.coffeeorderingwebapp.order.mapper.OrderMapper;
import com.example.coffeeorderingwebapp.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/orders")
@Validated
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper mapper;

    public OrderController(OrderService orderService, OrderMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    // 커피 주문 정보 등록
    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderPostDto orderPostDto) {
        Order order = mapper.orderPostDtoToOrder(orderPostDto);

        Order response = orderService.createOrder(order);

        return new ResponseEntity<>(mapper.orderToOrderResponseDto(response), HttpStatus.CREATED);
    }

    // 특정 주문 정보 조회
    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") @Positive long orderId) {
        Order response = orderService.findOrder(orderId);

        return new ResponseEntity<>(mapper.orderToOrderResponseDto(response), HttpStatus.OK);
    }

    // 모든 주문 정보 조회
    @GetMapping
    public ResponseEntity getOrders() {
        List<Order> orders = orderService.findOrders();

        List<OrderResponseDto> response = mapper.ordersToOrderResponseDtos(orders);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 특정 주문 정보 삭제
    @DeleteMapping("/{order-id}")
    public ResponseEntity cancelOrder(@PathVariable("order-id") long orderId) {
        orderService.cancelOrder();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
