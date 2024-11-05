package org.example.restspringbootudemy.services;

import org.example.restspringbootudemy.controllers.exceptions.UnsupportedMathOperationException;
import org.springframework.stereotype.Service;

@Service
public class MathService {

    private Double convertToDouble(String strNumber) {
        String number = strNumber.replaceAll(",", ".");
        return Double.parseDouble(number);
    }

    public boolean isNumber(String strNumber) {
        if (strNumber == null) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public Double sum(String a, String b) {
        if (!isNumber(a) || !isNumber(b)) {
            throw new UnsupportedMathOperationException("Set a numeric value.");
        }
        return convertToDouble(a)+convertToDouble(b);
    }

    public Double sub(String a, String b) {
        if (!isNumber(a) || !isNumber(b)) {
            throw new UnsupportedMathOperationException("Set a numeric value.");
        }
        return convertToDouble(a) - convertToDouble(b);
    }

    public Double mul(String a, String b) {
        if (!isNumber(a) || !isNumber(b)) {
            throw new UnsupportedMathOperationException("Set a numeric value.");
        }
        return convertToDouble(a) * convertToDouble(b);
    }

    public Double div(String a, String b) {
        if (!isNumber(a) || !isNumber(b)) {
            throw new UnsupportedMathOperationException("Set a numeric value.");
        }
        return convertToDouble(a) / convertToDouble(b);
    }

    public Double mean(String a, String b) {
        if (!isNumber(a) || !isNumber(b)) {
            throw new UnsupportedMathOperationException("Set a numeric value.");
        }
        return (convertToDouble(a) + convertToDouble(b)) / 2;
    }

    public Double squareRoot(String number) {
        if (!isNumber(number)) {
            throw new UnsupportedMathOperationException("Set a numeric value.");
        }
        return Math.sqrt(convertToDouble(number));
    }

}
