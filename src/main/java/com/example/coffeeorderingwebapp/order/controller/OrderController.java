package com.example.coffeeorderingwebapp.order.controller;

import com.example.coffeeorderingwebapp.member.entity.Member;
import com.example.coffeeorderingwebapp.member.service.MemberService;
import com.example.coffeeorderingwebapp.order.dto.OrderPatchDto;
import com.example.coffeeorderingwebapp.order.dto.OrderPostDto;
import com.example.coffeeorderingwebapp.order.entity.Order;
import com.example.coffeeorderingwebapp.order.entity.OrderCoffee;
import com.example.coffeeorderingwebapp.order.mapper.OrderMapper;
import com.example.coffeeorderingwebapp.order.service.OrderService;
import com.example.coffeeorderingwebapp.response.MultiResponseDto;
import com.example.coffeeorderingwebapp.response.SingleResponseDto;
import com.example.coffeeorderingwebapp.stamp.Stamp;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@RequestMapping("/v1/orders")
@Validated
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper mapper;
    private final MemberService memberService;

    public OrderController(OrderService orderService, OrderMapper mapper, MemberService memberService) {
        this.orderService = orderService;
        this.mapper = mapper;
        this.memberService = memberService;
    }

    // 커피 주문 정보 등록
    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderPostDto orderPostDto) {
        Order order = orderService.createOrder(mapper.orderPostDtoToOrder(orderPostDto));
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.orderToOrderResponseDto(order)), HttpStatus.CREATED);
    }

    private void patchOrder(Order order) {
        Member member = memberService.findMember(order.getMember().getMemberId());
        int stampCount = order.getOrderCoffees().stream()
                        .map(OrderCoffee::getQuantity)
                        .mapToInt(quantity -> quantity)
                        .sum();
        Stamp stamp = member.getStamp();
        stamp.setStampCount(stamp.getStampCount() + stampCount);
        member.setStamp(stamp);
        memberService.updateMember(member);
    }

    // 특정 주문 정보 갱신
    @PatchMapping("/{order-id}")
    public ResponseEntity patchOrder(@PathVariable("order-id") @Positive long orderId,
                                     @Valid @RequestBody OrderPatchDto orderPatchDto) {
        orderPatchDto.setOrderId(orderId);
        Order order = orderService.updateOrder(mapper.orderPatchDtoToOrder(orderPatchDto));
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.orderToOrderResponseDto(order)), HttpStatus.OK);
    }

    // 특정 주문 정보 조회
    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") @Positive long orderId) {
        Order order = orderService.findOrder(orderId);
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.orderToOrderResponseDto(order)), HttpStatus.OK);
    }

    // 모든 주문 정보 조회
    @GetMapping
    public ResponseEntity getOrders(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Order> pageOrders = orderService.findOrders(page - 1, size);
        List<Order> orders = pageOrders.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(mapper.ordersToOrderResponseDtos(orders), pageOrders), HttpStatus.OK);
    }

    // 특정 주문 정보 삭제
    @DeleteMapping("/{order-id}")
    public ResponseEntity cancelOrder(@PathVariable("order-id") @Positive long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
