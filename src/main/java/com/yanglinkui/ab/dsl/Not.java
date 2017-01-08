package com.yanglinkui.ab.dsl;

/**
 * Created by jonas on 2017/1/3.
 */
public class Not extends Equal {

    public boolean interpret(Context context) {
        return !super.interpret(context);
    }

    public String toString() {
        return this.variable.toString() + "!=" + this.value.toString();
    }
}
