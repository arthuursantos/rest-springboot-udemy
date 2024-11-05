package org.example.restspringbootudemy.controllers;

import org.example.restspringbootudemy.controllers.exceptions.UnsupportedMathOperationException;
import org.example.restspringbootudemy.services.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

    @Autowired
    private MathService mathService;

    @RequestMapping(value = "/sum/{number1}/{number2}")
    public Double sum(
            @PathVariable(value = "number1") String number1,
            @PathVariable(value = "number2") String number2
    ) {
        return mathService.sum(number1, number2);
    }

    @RequestMapping(value = "/subtraction/{number1}/{number2}")
    public Double subtraction(
            @PathVariable(value = "number1") String number1,
            @PathVariable(value = "number2") String number2
    ) {
        return mathService.sub(number1, number2);
    }

    @RequestMapping(value = "multiplication/{number1}/{number2}")
    public Double multiplication(
            @PathVariable(value = "number1") String number1,
            @PathVariable(value = "number2") String number2
    ) {
        return mathService.mul(number1, number2);
    }

    @RequestMapping(value = "/division/{number1}/{number2}")
    public Double division(
            @PathVariable(value = "number1") String number1,
            @PathVariable(value = "number2") String number2
    ) {
        return mathService.div(number1, number2);
    }

    @RequestMapping(value = "media/{number1}/{number2}")
    public Double mean(
            @PathVariable(value = "number1") String number1,
            @PathVariable(value = "number2") String number2
    ) {
        return mathService.mean(number1, number2);
    }

    @RequestMapping(value = "squareRoot/{number}")
    public Double squareRoot(
            @PathVariable(value = "number") String number
    ) {
        return mathService.squareRoot(number);
    }

}
