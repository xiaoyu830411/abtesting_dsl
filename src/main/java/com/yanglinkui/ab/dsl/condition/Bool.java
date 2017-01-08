package com.yanglinkui.ab.dsl.condition;

import com.yanglinkui.ab.dsl.Expression;

/**
 * Created by jonas on 2017/1/4.
 */
public abstract class Bool implements Expression {

    protected Expression left;

    protected Expression right;

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }
}
