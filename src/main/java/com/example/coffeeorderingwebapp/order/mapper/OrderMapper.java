package com.example.coffeeorderingwebapp.order.mapper;

import com.example.coffeeorderingwebapp.order.dto.OrderPostDto;
import com.example.coffeeorderingwebapp.order.dto.OrderResponseDto;
import com.example.coffeeorderingwebapp.order.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderPostDtoToOrder(OrderPostDto orderPostDto);
    OrderResponseDto orderToOrderResponseDto(Order order);
    List<OrderResponseDto> ordersToOrderResponseDtos(List<Order> orders);
}
