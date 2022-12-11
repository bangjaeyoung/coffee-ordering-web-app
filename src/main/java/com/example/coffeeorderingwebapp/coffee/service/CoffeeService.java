package com.example.coffeeorderingwebapp.coffee.service;

import com.example.coffeeorderingwebapp.coffee.entity.Coffee;
import com.example.coffeeorderingwebapp.coffee.repository.CoffeeRepository;
import com.example.coffeeorderingwebapp.exception.BusinessLogicException;
import com.example.coffeeorderingwebapp.exception.ExceptionCode;
import com.example.coffeeorderingwebapp.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;
    private final CustomBeanUtils<Coffee> beanUtils;

    public CoffeeService(CoffeeRepository coffeeRepository, CustomBeanUtils<Coffee> beanUtils) {
        this.coffeeRepository = coffeeRepository;
        this.beanUtils = beanUtils;
    }

    public Coffee createCoffee(Coffee coffee) {
        String coffeeCode = coffee.getCoffeeCode().toUpperCase();
        verifyExistCoffee(coffeeCode);
        coffee.setCoffeeCode(coffeeCode);
        return coffeeRepository.save(coffee);
    }

//    public Coffee updateCoffee(Coffee coffee) {
//        Coffee findCoffee = findVerifiedCoffee(coffee.getCoffeeId());
//        Optional.ofNullable(coffee.getKorName()).ifPresent(findCoffee::setKorName);
//        Optional.ofNullable(coffee.getEngName()).ifPresent(findCoffee::setEngName);
//        Optional.ofNullable(coffee.getPrice()).ifPresent(findCoffee::setPrice);
//        Optional.ofNullable(coffee.getCoffeeStatus()).ifPresent(findCoffee::setCoffeeStatus);
//
//        return coffeeRepository.save(findCoffee);
//    }

    public Coffee updateCoffee(Coffee coffee) {
        Coffee findCoffee = findVerifiedCoffee(coffee.getCoffeeId());
        beanUtils.copyNonNullProperties(coffee, findCoffee);
        return coffeeRepository.save(findCoffee);
    }

    public Coffee findCoffee(long coffeeId) {
        return findVerifiedCoffeeByQuery(coffeeId);
    }

    public Page<Coffee> findCoffees(int page, int size) {
        return coffeeRepository.findAll(PageRequest.of(page, size, Sort.by("coffeeId").descending()));
    }

    public void deleteCoffee(long coffeeId) {
        Coffee coffee = findVerifiedCoffee(coffeeId);
        coffeeRepository.delete(coffee);
    }

    public Coffee findVerifiedCoffee(long coffeeId) {
        Optional<Coffee> optionalCoffee = coffeeRepository.findById(coffeeId);
        Coffee findCoffee = optionalCoffee.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));
        return findCoffee;
    }

    private void verifyExistCoffee(String coffeeCode) {
        Optional<Coffee> coffee = coffeeRepository.findByCoffeeCode(coffeeCode);
        if(coffee.isPresent()) throw new BusinessLogicException(ExceptionCode.COFFEE_CODE_EXISTS);
    }

    private Coffee findVerifiedCoffeeByQuery(long coffeeId) {
        Optional<Coffee> optionalCoffee = coffeeRepository.findByCoffee(coffeeId);
        Coffee findCoffee = optionalCoffee.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));
        return findCoffee;
    }
}
