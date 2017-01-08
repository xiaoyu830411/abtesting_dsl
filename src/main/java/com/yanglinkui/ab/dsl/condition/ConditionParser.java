package com.yanglinkui.ab.dsl.condition;

import com.yanglinkui.ab.dsl.*;
import com.yanglinkui.ab.dsl.Number;

import java.math.BigDecimal;
import java.util.HashSet;

/**
 * Created by jonas on 2017/1/3.
 * expression: (function|name)(=|!=|>|>=|<|<=)NUMBER|STRING|[(NUMBER|STRING)
 * statements: * || expression(('&&'|'||')expression)*
 */
public class ConditionParser extends Parser {


    public ConditionParser(ConditionLexer input) {
        super(input, 2);
    }

    protected String getTokenName(int type) {
        return ConditionToken.getTokenName(type);
    }

    public Statements statements() {
        Statements s = new Statements();

        Star star = star();
        if (star != null) {
            s.setExpression(star);
            return s;
        }

        return _statements();
    }

    Statements _statements() {
        Statements s = new Statements();

        Expression left = null;
        Expression right = null;

        left = brack();
        if (left == null) {
            left = expression();
        }

        while (LA(1) == ConditionToken.TOKEN_BOOL) {
            Bool bool = bool();

            if (LA(1) == ConditionToken.TOKEN_LBRACK) {
                right = brack();
            } else {
                right = expression();
            }

            bool.setLeft(left);
            bool.setRight(right);

            left = bool


            ;
        }

        s.setExpression(left);
        return s;
    }

    Star star() {
        if (LA(1) == ConditionToken.TOKEN_STAR) {
            if (LA(2) != ConditionToken.TOKEN_EOF) {
                throw new Error("* must be just one ");
            }

            Token t = match(ConditionToken.TOKEN_STAR);
            return new Star();
        }

        return null;
    }

    Expression brack() {
        Expression expression = null;

        if (LA(1) == ConditionToken.TOKEN_LBRACK) {
            consume();
            expression = _statements();
            match(ConditionToken.TOKEN_RBRACK);
        }

        return expression;
    }

    Expression expression() {
        Variable var = null;
        if (LA(1) == ConditionToken.TOKEN_NAME && LA(2) == ConditionToken.TOKEN_LBRACK) {
            var = function();
        } else if (LA(1) == ConditionToken.TOKEN_NAME) {
            var = name();
        }

        Operation operation = op();


        Value values = value();

        operation.setVariable(var);
        operation.setValue(values);

        return operation;
    }

    public Function function() {
        if (LA(1) == ConditionToken.TOKEN_NAME && LA(2) == ConditionToken.TOKEN_LBRACK) {
            Token name = match(ConditionToken.TOKEN_NAME);
            Variable var = null;
            match(ConditionToken.TOKEN_LBRACK);
            if (LA(1) == ConditionToken.TOKEN_NAME) {
                var = name();
            }

            match(ConditionToken.TOKEN_RBRACK);

            return new Function(name.getText(), var);
        }

        return null;
    }

    public Variable name() {
        Token t = match(ConditionToken.TOKEN_NAME);

        return new Variable(t.getText());
    }

    Operation op() {
        Token t = match(ConditionToken.TOKEN_OP);

        if ("=".equals(t.getText())) {
            return new Equal();
        }

        if ("!=".equals(t.getText())) {
            return new Not();
        }

        if (LA(1) != ConditionToken.TOKEN_NUMBER) {
            throw new Error("The >, >=, <, <= is expecting a " + ConditionToken.getTokenName(ConditionToken.TOKEN_NUMBER) + "; found "+LT(1));
        }

        if (">".equals(t.getText())) {
            return new Greater();
        }

        if (">=".equals(t.getText())) {
            return new GreaterEqual();
        }

        if ("<".equals(t.getText())) {
            return new Less();
        }

        return new LessEqual();
    }

    Value value() {
        if (LA(1) == ConditionToken.TOKEN_NUMBER || LA(1) == ConditionToken.TOKEN_STRING) {
            return element();
        } else {
            match(ConditionToken.TOKEN_LSBRACK);
            Value value = elements();
            match(ConditionToken.TOKEN_RSBRACK);

            return value;
        }
    }

    Array elements() {
        HashSet<Value> list = new HashSet<Value>();

        list.add(element());
        while (LA(1) == ConditionToken.TOKEN_COMMA) {
            match(ConditionToken.TOKEN_COMMA);
            list.add(element());
        }

        return new Array(list);
    }

    Value element() {
        if (LA(1) == ConditionToken.TOKEN_NUMBER) {
            Token t = match(ConditionToken.TOKEN_NUMBER);
            return new Number(new BigDecimal(t.getText()));
        } else {
            Token t = match(ConditionToken.TOKEN_STRING);
            return new MyString(t.getText());
        }

    }

    public Bool bool() {
        Token t = match(ConditionToken.TOKEN_BOOL);
        if ("||".equals(t.getText())) {
            return new Or();
        }

        return new And();
    }

    public static void main(String[] args) {
        ConditionLexer l = new ConditionLexer("mod(user.id)=['1','2'] && (client.ip>3.1 || client.browser!=['safari'])");

        ConditionParser p = new ConditionParser(l);
        Statements statements = p.statements();

        System.out.println(statements);
        System.out.println(statements.interpret(new Context() {

            public String getValue(Variable var) {
                System.out.println(var + "-var");
                return "4";
            }

            public String getValue(Function function) {
                System.out.println(function);
                return "2";
            }
        }));
    }
}
