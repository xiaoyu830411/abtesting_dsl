package com.yanglinkui.ab.dsl.condition;

import com.yanglinkui.ab.dsl.Context;
import com.yanglinkui.ab.dsl.Expression;

/**
 * Created by jonas on 2017/1/4.
 */
public class Statements implements Expression {

    private Expression expression;

    public Statements() {}

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public boolean interpret(Context context) {
        return this.expression.interpret(context);
    }

    public String toString() {
        return this.expression.toString();
    }
}
