package com.example.coffeeorderingwebapp.order.dto;

import com.example.coffeeorderingwebapp.member.entity.Member;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
public class OrderPostDto {
    @Positive
    private long memberId;

    @NotNull
    @Valid
    private List<OrderCoffeeDto> orderCoffees;

    public Member getMember() {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }
}
