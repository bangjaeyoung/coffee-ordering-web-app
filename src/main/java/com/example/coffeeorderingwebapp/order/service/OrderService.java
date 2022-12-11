package com.example.coffeeorderingwebapp.order.service;

import com.example.coffeeorderingwebapp.coffee.service.CoffeeService;
import com.example.coffeeorderingwebapp.exception.BusinessLogicException;
import com.example.coffeeorderingwebapp.exception.ExceptionCode;
import com.example.coffeeorderingwebapp.member.entity.Member;
import com.example.coffeeorderingwebapp.member.service.MemberService;
import com.example.coffeeorderingwebapp.order.entity.Order;
import com.example.coffeeorderingwebapp.order.entity.OrderCoffee;
import com.example.coffeeorderingwebapp.order.repository.OrderRepository;
import com.example.coffeeorderingwebapp.stamp.Stamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final MemberService memberService;
    private final OrderRepository orderRepository;
    private final CoffeeService coffeeService;

    public OrderService(MemberService memberService, OrderRepository orderRepository, CoffeeService coffeeService) {
        this.memberService = memberService;
        this.orderRepository = orderRepository;
        this.coffeeService = coffeeService;
    }

    public Order createOrder(Order order) {
        verifyOrder(order);
        Order savedOrder = saveOrder(order);
        updateStamp(savedOrder);
        return savedOrder;
    }

    public Order updateOrder(Order order) {
        Order findOrder = findVerifiedOrder(order.getOrderId());
        Optional.ofNullable(order.getOrderStatus()).ifPresent(findOrder::setOrderStatus);
        return saveOrder(findOrder);
    }

    public Order findOrder(long orderId) {
        return findVerifiedOrder(orderId);
    }

    public Page<Order> findOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size, Sort.by("orderId").descending()));
    }

    public void cancelOrder(long orderId) {
        Order findOrder = findVerifiedOrder(orderId);
        int step = findOrder.getOrderStatus().getStepNumber();
        if (step >= 2) throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        findOrder.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);
        orderRepository.save(findOrder);
    }

    private Order findVerifiedOrder(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order findOrder = optionalOrder.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrder;
    }

    private void verifyOrder(Order order) {
        memberService.findVerifiedMember(order.getMember().getMemberId());
        order.getOrderCoffees()
                .forEach(orderCoffee -> coffeeService.findVerifiedCoffee(orderCoffee.getCoffee().getCoffeeId()));
    }

    private void updateStamp(Order order) {
        Member member = memberService.findMember(order.getMember().getMemberId());
        int stampCount = calculateStampCount(order);
        Stamp stamp = member.getStamp();
        stamp.setStampCount(stamp.getStampCount() + stampCount);
        member.setStamp(stamp);
        memberService.updateMember(member);
    }

    private int calculateStampCount(Order order) {
        return order.getOrderCoffees().stream()
                .map(OrderCoffee::getQuantity)
                .mapToInt(quantity -> quantity)
                .sum();
    }

    private Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
