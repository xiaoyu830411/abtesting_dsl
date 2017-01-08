package com.yanglinkui.ab.dsl.condition;

import com.yanglinkui.ab.dsl.Expression;
import com.yanglinkui.ab.dsl.Context;

/**
 * Created by jonas on 2017/1/3.
 */
public class Star implements Expression {

    public boolean interpret(Context context) {
        return true;
    }

    public String toString() {
        return "*";
    }
}
