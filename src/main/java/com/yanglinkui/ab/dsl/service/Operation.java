package com.yanglinkui.ab.dsl.service;

/**
 * Created by jonas on 2017/1/4.
 */
public abstract class Operation implements Expression {

    protected Variable left;

    protected Value value;

    public Variable getLeft() {
        return left;
    }

    public void setLeft(Variable left) {
        this.left = left;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
