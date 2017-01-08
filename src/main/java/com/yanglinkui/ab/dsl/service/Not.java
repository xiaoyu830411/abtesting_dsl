package com.yanglinkui.ab.dsl.service;

/**
 * Created by jonas on 2017/1/3.
 */
public class Not extends Equal {

    public boolean interpret(Context context) {
        return !super.interpret(context);
    }

    public String toString() {
        return this.left.toString() + "!=" + this.value.toString();
    }
}
