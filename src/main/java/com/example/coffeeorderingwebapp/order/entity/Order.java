package com.example.coffeeorderingwebapp.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private long memberId;
    private long coffeeId;
}
