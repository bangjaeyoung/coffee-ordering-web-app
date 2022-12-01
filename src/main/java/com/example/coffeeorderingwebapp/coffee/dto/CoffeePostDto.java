package com.example.coffeeorderingwebapp.coffee.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CoffeePostDto {
    @NotBlank
    @Pattern(regexp = "^([A-Za-z])(\\s?[A-Za-z])*$",
            message = "커피명(영문)은 영문이어야 합니다. 예) Cafe Latte")
    private String engName;

    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    private String korName;

    @Range(min = 100, max = 50000)
    private int price;

    public String getEngName() {
        return engName;
    }

    public String getKorName() {
        return korName;
    }

    public int getPrice() {
        return price;
    }
}
