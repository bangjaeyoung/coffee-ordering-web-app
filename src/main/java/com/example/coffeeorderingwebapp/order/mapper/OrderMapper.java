package com.example.coffeeorderingwebapp.order.mapper;

import com.example.coffeeorderingwebapp.coffee.entity.Coffee;
import com.example.coffeeorderingwebapp.member.entity.Member;
import com.example.coffeeorderingwebapp.order.dto.OrderCoffeeResponseDto;
import com.example.coffeeorderingwebapp.order.dto.OrderPatchDto;
import com.example.coffeeorderingwebapp.order.dto.OrderPostDto;
import com.example.coffeeorderingwebapp.order.dto.OrderResponseDto;
import com.example.coffeeorderingwebapp.order.entity.Order;
import com.example.coffeeorderingwebapp.order.entity.OrderCoffee;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderPatchDtoToOrder(OrderPatchDto orderPatchDto);

    List<OrderResponseDto> ordersToOrderResponseDtos(List<Order> orders);

    default Order orderPostDtoToOrder(OrderPostDto orderPostDto) {
        Order order = new Order();
        Member member = new Member();
        member.setMemberId(orderPostDto.getMemberId());
        List<OrderCoffee> orderCoffees = orderPostDto.getOrderCoffees().stream()
                .map(orderCoffeeDto -> {
                    OrderCoffee orderCoffee = new OrderCoffee();
                    Coffee coffee = new Coffee();
                    coffee.setCoffeeId(orderCoffeeDto.getCoffeeId());
                    orderCoffee.addOrder(order);
                    orderCoffee.addCoffee(coffee);
                    orderCoffee.setQuantity(orderCoffeeDto.getQuantity());
                    return orderCoffee;
                }).collect(Collectors.toList());
        order.addMember(member);
        order.setOrderCoffees(orderCoffees);

        return order;
    }

    default OrderResponseDto orderToOrderResponseDto(Order order) {
        // TODO 객체 그래프 탐색을 통해 주문한 커피 정보를 가져오기 때문에, N+1 문제가 발생할 수 있다.
        List<OrderCoffee> orderCoffees = order.getOrderCoffees();
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderId(order.getOrderId());
        orderResponseDto.setMemberId(order.getMember().getMemberId());
        orderResponseDto.setOrderStatus(order.getOrderStatus());
        orderResponseDto.setCreatedAt(order.getCreatedAt());
        orderResponseDto.setOrderCoffees(orderCoffeesToOrderCoffeeResponseDtos(orderCoffees));
        return orderResponseDto;
    }

    default List<OrderCoffeeResponseDto> orderCoffeesToOrderCoffeeResponseDtos(List<OrderCoffee> orderCoffees) {
        return orderCoffees
                .stream()
                .map(orderCoffee -> OrderCoffeeResponseDto
                        .builder()
                        .coffeeId(orderCoffee.getCoffee().getCoffeeId())
                        .quantity(orderCoffee.getQuantity())
                        .price(orderCoffee.getCoffee().getPrice())
                        .korName(orderCoffee.getCoffee().getKorName())
                        .engName(orderCoffee.getCoffee().getEngName())
                        .build())
                .collect(Collectors.toList());
    }
}
