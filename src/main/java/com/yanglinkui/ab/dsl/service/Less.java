package com.yanglinkui.ab.dsl.service;

import java.math.BigDecimal;

/**
 * Created by jonas on 2017/1/3.
 */
public class Less extends Operation {

    public boolean interpret(Context context) {
        String leftValue = left.getValue(context);
        return new BigDecimal(leftValue).compareTo(((Number)this.value).getValue()) < 0;
    }

    public String toString() {
        return this.left.toString() + "<" + this.value.toString();
    }
}
