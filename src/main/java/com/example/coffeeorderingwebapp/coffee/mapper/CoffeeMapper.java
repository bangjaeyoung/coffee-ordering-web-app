package com.example.coffeeorderingwebapp.coffee.mapper;

import com.example.coffeeorderingwebapp.coffee.dto.CoffeePatchDto;
import com.example.coffeeorderingwebapp.coffee.dto.CoffeePostDto;
import com.example.coffeeorderingwebapp.coffee.dto.CoffeeResponseDto;
import com.example.coffeeorderingwebapp.coffee.entity.Coffee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {
    Coffee coffeePostDtoToCoffee(CoffeePostDto coffeePostDto);
    Coffee coffeePatchDtoToCoffee(CoffeePatchDto coffeePatchDto);
    CoffeeResponseDto coffeeToCoffeeResponseDto(Coffee coffee);
    List<CoffeeResponseDto> coffeesToCoffeeResponseDtos(List<Coffee> coffees);
}
