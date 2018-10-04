package com.github.fangming.springboot.web;

import com.github.fangming.springboot.model.CalculationResult;
import com.github.fangming.springboot.service.ComplicatedCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculateController {

    private final ComplicatedCalculationService calculationService;

    @Autowired
    public CalculateController(ComplicatedCalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping(value = "/{a}/{b}",produces = "application/json")
    public CalculationResult calculate(@PathVariable("a") int a, @PathVariable("b") int b) {
        return calculationService.calculate(a, b);
    }
}
