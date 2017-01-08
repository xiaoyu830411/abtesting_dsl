package com.yanglinkui.ab.dsl.service;

import com.yanglinkui.ab.dsl.*;

import java.lang.*;
import java.math.BigDecimal;
import java.util.HashSet;

/**
 * Created by jonas on 2017/1/6.
 * expression: name(=|!=|>|>=|<|<=)NUMBER|STRING|[(NUMBER|STRING)
 * statements: expression(,expression)*
 */
public class ServiceParser extends Parser {

    public ServiceParser(Lexer input) {
        super(input, 2);
    }

    protected String getTokenName(int type) {
        return ServiceToken.getTokenName(type);
    }

    public Statements statements() {
        Statements s = new Statements();

        Operation operation = expression();
        s.putOperation(operation.getVariable().getId(), operation);

        while (LA(1) == ServiceToken.TOKEN_COMMA) {
            consume();

            operation = expression();
            if (s.getOperation(operation.getVariable().getId()) != null) {
                throw new Error("The " + operation.getVariable().getId() + " is duplicate.");
            }
            s.putOperation(operation.getVariable().getId(), operation);

        }

        return s;
    }

    Operation expression() {
        Variable var = name();
        Operation operation = op();
        Value value = value();

        operation.setVariable(var);
        operation.setValue(value);

        return operation;
    }

    Variable name() {
        Token t = match(ServiceToken.TOKEN_NAME);

        return new Variable(t.getText());
    }

    Operation op() {
        Token t = match(ServiceToken.TOKEN_OP);

        if ("=".equals(t.getText())) {
            return new Equal();
        }

        if ("!=".equals(t.getText())) {
            return new Not();
        }

        if (LA(1) != ServiceToken.TOKEN_NUMBER) {
            throw new Error("The >, >=, <, <= is expecting a " + ServiceToken.getTokenName(ServiceToken.TOKEN_NUMBER) + "; found "+LT(1));
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
        if (LA(1) == ServiceToken.TOKEN_NUMBER || LA(1) == ServiceToken.TOKEN_STRING) {
            return element();
        } else {
            match(ServiceToken.TOKEN_LSBRACK);
            Value value = elements();
            match(ServiceToken.TOKEN_RSBRACK);

            return value;
        }
    }

    Array elements() {
        HashSet<Value> list = new HashSet<Value>();

        list.add(element());
        while (LA(1) == ServiceToken.TOKEN_COMMA) {
            match(ServiceToken.TOKEN_COMMA);
            list.add(element());
        }

        return new Array(list);
    }

    Value element() {
        if (LA(1) == ServiceToken.TOKEN_NUMBER) {
            Token t = match(ServiceToken.TOKEN_NUMBER);
            return new com.yanglinkui.ab.dsl.Number(new BigDecimal(t.getText()));
        } else {
            Token t = match(ServiceToken.TOKEN_STRING);
            return new MyString(t.getText());
        }

    }

    public static void main(String[] args) {
        ServiceLexer l = new ServiceLexer("s1.version=0, s2.version > 1.1, s3.status='test', s4.ip !=['192.168.1.10', 4]");
        ServiceParser p = new ServiceParser(l);
        Statements s = p.statements();
        Expression e = s.getOperation("s4.ip");
        System.out.println(e.interpret(new ServiceContext() {
            public String getValue(Variable var) {
                return "192.168.1.11";
            }
        }));
    }

}
