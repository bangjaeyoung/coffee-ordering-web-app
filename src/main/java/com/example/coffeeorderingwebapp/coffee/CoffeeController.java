package com.example.coffeeorderingwebapp.coffee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/coffees")
public class CoffeeController {

    // 커피 정보 등록
    @PostMapping
    public ResponseEntity postCoffee(@RequestBody CoffeePostDto coffeePostDto) {
        return new ResponseEntity<>(coffeePostDto, HttpStatus.CREATED);
    }

    // 커피 정보 수정
    @PatchMapping("/{coffee-id}")
    public ResponseEntity patchCoffee(@PathVariable("coffee-id") long coffeeId,
                                      @RequestBody CoffeePatchDto coffeePatchDto) {
        coffeePatchDto.setCoffeeId(coffeeId);
        coffeePatchDto.setEngName("Vanilla Latte");
        coffeePatchDto.setKorName("바닐라 라떼");
        return new ResponseEntity<>(coffeePatchDto, HttpStatus.OK);
    }

    // 하나의 커피 정보 조회
    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id") long coffeeId) {
        System.out.println("# coffee-id: " + coffeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 모든 커피 정보 조회
    @GetMapping
    public ResponseEntity getCoffees() {
        System.out.println("# get Coffees");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 특정 커피 정보 삭제
    @DeleteMapping("/{coffee-id}")
    public ResponseEntity deleteCoffee(@PathVariable("coffee-id") long coffeeId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
