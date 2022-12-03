package com.example.coffeeorderingwebapp.order.dto;

import javax.validation.constraints.Positive;

public class OrderPostDto {
    @Positive
    private long memberId;

    @Positive
    private long coffeeId;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getCoffeeId() {
        return coffeeId;
    }

    public void setCoffeeId(long coffeeId) {
        this.coffeeId = coffeeId;
    }
}
