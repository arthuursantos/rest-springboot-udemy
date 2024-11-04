package org.example.restspringbootudemy.controllers;

import org.example.restspringbootudemy.controllers.exceptions.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

    // mapeia uma requisição para um metodo
    @RequestMapping(value = "/sum/{number1}/{number2}")
    public Double sum(
            @PathVariable(value = "number1") String number1,
            @PathVariable(value = "number2") String number2
    ) throws Exception {

        if (!isNumber(number1) || !isNumber(number2)) {
            throw new UnsupportedMathOperationException("Set a numeric value.");
        }

        return convertToDouble(number1)+convertToDouble(number2);
    }

    private Double convertToDouble(String strNumber) {
        String number = strNumber.replaceAll(",", ".");
        return Double.parseDouble(number);
    }

    public boolean isNumber(String strNumber) {
        if (strNumber == null) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

}
