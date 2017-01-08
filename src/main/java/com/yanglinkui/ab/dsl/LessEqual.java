package com.yanglinkui.ab.dsl;

import java.lang.*;
import java.math.BigDecimal;

/**
 * Created by jonas on 2017/1/4.
 */
public class LessEqual extends Operation {

    public boolean interpret(Context context) {
        String leftValue = variable.getValue(context);
        return new BigDecimal(leftValue).compareTo(((Number)this.value).getValue()) <= 0;
    }

    public String toString() {
        return this.variable.toString() + "<=" + this.value.toString();
    }
}
