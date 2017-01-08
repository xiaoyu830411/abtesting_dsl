package com.yanglinkui.ab.dsl.condition;

import com.yanglinkui.ab.dsl.Token;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by jonas on 2017/1/8.
 */
public class TestConditionLexer {

    @Test
    public void testOP() {
        ConditionLexer l = new ConditionLexer("=");
        Token t = l.OP();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), "=");

        l = new ConditionLexer(">");
        t = l.OP();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), ">");

        l = new ConditionLexer("<");
        t = l.OP();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), "<");

        l = new ConditionLexer(">=");
        t = l.OP();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), ">=");

        l = new ConditionLexer("<=");
        t = l.OP();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), "<=");

        l = new ConditionLexer("!=");
        t = l.OP();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), "!=");

        l = new ConditionLexer("!");

        try {
            t = l.OP();
            Assert.fail("It should be failed. Because it want to get = ");
        } catch (Error e) {
        }
    }

    @Test
    public void testNAMEORNUMBER() {
        ConditionLexer l = null;
        Token t = null;
        l = new ConditionLexer("12345");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_NUMBER);
        Assert.assertEquals(t.getText(), "12345");

        l = new ConditionLexer("1.1");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_NUMBER);
        Assert.assertEquals(t.getText(), "1.1");

        l = new ConditionLexer("1.1.1");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_NAME);
        Assert.assertEquals(t.getText(), "1.1.1");

        l = new ConditionLexer("1.1.");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_NAME);
        Assert.assertEquals(t.getText(), "1.1.");

        l = new ConditionLexer(".1.1");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_NAME);
        Assert.assertEquals(t.getText(), ".1.1");

        l = new ConditionLexer("abc_-1234567890.aa.");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ConditionToken.TOKEN_NAME);
        Assert.assertEquals(t.getText(), "abc_-1234567890.aa.");
    }

    @Test
    public void testNextToken() {
        ConditionLexer l = new ConditionLexer("* || mod(user.id)=['1','2'] && (client.ip>3.1 || client.browser!=['safari'])");
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_STAR);//*
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_BOOL);//||
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_NAME);//mod
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_LBRACK);//c
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_NAME);//user.id
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_RBRACK);//)
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_OP);//=
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_LSBRACK);//[
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_STRING);//'1'
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_COMMA);//,
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_STRING);//'2'
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_RSBRACK);//]
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_BOOL);//&&
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_LBRACK);//(
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_NAME);//client.ip
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_OP);//>
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_NUMBER);//3.1
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_BOOL);//||
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_NAME);//client.browser
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_OP);//!=
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_LSBRACK);//[
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_STRING);//'safari'
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_RSBRACK);//]
        Assert.assertEquals(l.nextToken().getType(), ConditionToken.TOKEN_RBRACK);//)

    }
}
