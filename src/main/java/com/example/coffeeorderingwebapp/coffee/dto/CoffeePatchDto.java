package com.example.coffeeorderingwebapp.coffee.dto;

import com.example.coffeeorderingwebapp.validator.NotSpace;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import java.util.Optional;

public class CoffeePatchDto {
    private long coffeeId;

    @Pattern(regexp = "^([A-Za-z])(\\s?[A-Za-z])*$",
            message = "커피명(영문)은 영문이어야 합니다. 예) Cafe Latte")
    private String engName;

        @NotSpace(message = "커피명(한글)은 공백이 아니어야 합니다.")
        private String korName;


        private Optional<@Range(min = 100, max = 50000) Integer> price = Optional.empty();

        public long getCoffeeId() {
            return coffeeId;
        }

        public void setCoffeeId(long coffeeId) {
        this.coffeeId = coffeeId;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getKorName() {
        return korName;
    }

    public void setKorName(String korName) {
        this.korName = korName;
    }

    public Optional<Integer> getPrice() {
        return price;
    }

    public void setPrice(Optional<Integer> price) {
        this.price = price;
    }
}
