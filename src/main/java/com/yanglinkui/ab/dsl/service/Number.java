package com.yanglinkui.ab.dsl.service;

import java.math.BigDecimal;

/**
 * Created by jonas on 2017/1/7.
 */
public class Number implements Value<BigDecimal> {

    private final BigDecimal value;

    public Number(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Number: " + this.value;
    }
}
