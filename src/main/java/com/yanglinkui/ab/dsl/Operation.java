package com.yanglinkui.ab.dsl;

/**
 * Created by jonas on 2017/1/4.
 */
public abstract class Operation implements Expression {

    protected Variable variable;

    protected Value value;

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

}
