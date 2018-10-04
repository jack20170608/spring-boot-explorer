package com.github.fangming.springboot.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class CalculationResult {

    private int number1;
    private int number2;
    private int result;

    public CalculationResult() {
    }

    public CalculationResult(int number1, int number2, int result) {
        this.number1 = number1;
        this.number2 = number2;
        this.result = result;
    }

    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

    public int getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalculationResult)) return false;
        CalculationResult that = (CalculationResult) o;
        return number1 == that.number1 &&
            number2 == that.number2 &&
            result == that.result;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number1, number2, result);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("number1", number1)
            .add("number2", number2)
            .add("result", result)
            .toString();
    }
}
