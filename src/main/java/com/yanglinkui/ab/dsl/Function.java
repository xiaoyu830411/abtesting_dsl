package com.yanglinkui.ab.dsl;

/**
 * Created by jonas on 2017/1/4.
 */
public class Function extends Variable {

    protected Variable var;

    public Function(String func, Variable var) {
        super(func);
        this.var = var;
    }

    public String getValue(Context context) {
        return context.getValue(this);
    }

    public String toString() {
        return this.id + "(" + (this.var == null ? "" : this.var.toString()) + ")";
    }
}
