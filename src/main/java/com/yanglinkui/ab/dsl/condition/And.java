package com.yanglinkui.ab.dsl.condition;

import com.yanglinkui.ab.dsl.Context;

/**
 * Created by jonas on 2017/1/3.
 */
public class And extends Bool {

    public boolean interpret(Context context) {
        return this.left.interpret(context) && this.right.interpret(context);
    }

    public String toString() {
        return "(" + this.left.toString() + " && " + this.right.toString() + ")";
    }
}
