package com.yanglinkui.ab.dsl.condition;

import com.yanglinkui.ab.dsl.*;
import com.yanglinkui.ab.dsl.Number;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

/**
 * Created by jonas on 2017/1/8.
 */
public class TestConditionParser {

    @Test
    public void testStar() {
        ConditionLexer l = null;
        ConditionParser p = null;
        Statements s = null;

        l = new ConditionLexer("*");
        p = new ConditionParser(l);
        s = p.statements();
        Assert.assertNotNull(s);

        Assert.assertTrue(s.interpret(new Context() {
            public String getValue(Variable var) {
                return null;
            }

            public String getValue(Function function) {
                return null;
            }
        }));

        try {
            l = new ConditionLexer("* || user.id='userId'");
            p = new ConditionParser(l);
            s = p.statements();

            Assert.fail("The * must be just one element");
        } catch (Error e) {}

        try {
            l = new ConditionLexer("user.id='userId' && *");
            p = new ConditionParser(l);
            s = p.statements();

            Assert.fail("The * must be just one element");
        } catch (Error e) {}
    }

    @Test
    public void testStatements() {
        ConditionLexer l = new ConditionLexer("mod(user.id)>500 && (client.ip='192.168.10.1' || client.browser!=['safari'])");
        ConditionParser p = new ConditionParser(l);

        Statements s = p.statements();
        Assert.assertNotNull(s);

        Assert.assertTrue(s.interpret(new Context() {
            public String getValue(Variable var) {
                if (var.getId().equals("client.ip")) {
                    return "192.168.10.1";
                }

                if (var.getId().equals("client.browser")) {
                    return "safari";
                }

                return null;
            }

            public String getValue(Function function) {
                return "501";
            }
        }));

        Assert.assertTrue(s.interpret(new Context() {
            public String getValue(Variable var) {
                if (var.getId().equals("client.ip")) {
                    return "192.168.10.2";
                }

                if (var.getId().equals("client.browser")) {
                    return "IE";
                }

                return null;
            }

            public String getValue(Function function) {
                return "501";
            }
        }));

        Assert.assertFalse(s.interpret(new Context() {
            public String getValue(Variable var) {
                if (var.getId().equals("client.ip")) {
                    return "192.168.10.1";
                }

                if (var.getId().equals("client.browser")) {
                    return "IE";
                }

                return null;
            }

            public String getValue(Function function) {
                return "500";
            }
        }));

        Assert.assertFalse(s.interpret(new Context() {
            public String getValue(Variable var) {
                if (var.getId().equals("client.ip")) {
                    return "192.168.10.2";
                }

                if (var.getId().equals("client.browser")) {
                    return "safari";
                }

                return null;
            }

            public String getValue(Function function) {
                return "501";
            }
        }));

    }

    @Test
    public void testOperationForNumber() {
        ConditionLexer l = null;
        ConditionParser p = null;
        Statements s = null;
        l = new ConditionLexer("s1.version='1'");
        p = new ConditionParser(l);
        try {
            s = p.statements();
        } catch (Error e) {
            Assert.fail("The operation(=) does not support string.");
        }

        l = new ConditionLexer("s1.version>'1'");
        p = new ConditionParser(l);
        try {
            s = p.statements();
            Assert.fail("The operation(>) just support number, but it does not work.");
        } catch (Error e) {
        }

        l = new ConditionLexer("s1.version>='1'");
        p = new ConditionParser(l);
        try {
            s = p.statements();
            Assert.fail("The operation(>=) just support number, but it does not work.");
        } catch (Error e) {
        }

        l = new ConditionLexer("s1.version<'1'");
        p = new ConditionParser(l);
        try {
            s = p.statements();
            Assert.fail("The operation(<) just support number, but it does not work.");
        } catch (Error e) {
        }

        l = new ConditionLexer("s1.version<='1'");
        p = new ConditionParser(l);
        try {
            s = p.statements();
            Assert.fail("The operation(<=) just support number, but it does not work.");
        } catch (Error e) {
        }
    }
}
