package com.yanglinkui.ab.dsl.service;

import com.yanglinkui.ab.dsl.Token;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by jonas on 2017/1/8.
 */
public class TestServiceLexer {

    @Test
    public void testOP() {
        ServiceLexer l = new ServiceLexer("=");
        Token t = l.OP();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), "=");

        l = new ServiceLexer(">");
        t = l.OP();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), ">");

        l = new ServiceLexer("<");
        t = l.OP();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), "<");

        l = new ServiceLexer(">=");
        t = l.OP();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), ">=");

        l = new ServiceLexer("<=");
        t = l.OP();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), "<=");

        l = new ServiceLexer("!=");
        t = l.OP();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_OP);
        Assert.assertEquals(t.getText(), "!=");

        l = new ServiceLexer("!");

        try {
            t = l.OP();
            Assert.fail("It should be failed. Because it want to get = ");
        } catch (Error e) {
        }
    }

    @Test
    public void testNAMEORNUMBER() {
        ServiceLexer l = null;
        Token t = null;
        l = new ServiceLexer("12345");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_NUMBER);
        Assert.assertEquals(t.getText(), "12345");

        l = new ServiceLexer("1.1");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_NUMBER);
        Assert.assertEquals(t.getText(), "1.1");

        l = new ServiceLexer("1.1.1");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_NAME);
        Assert.assertEquals(t.getText(), "1.1.1");

        l = new ServiceLexer("1.1.");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_NAME);
        Assert.assertEquals(t.getText(), "1.1.");

        l = new ServiceLexer(".1.1");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_NAME);
        Assert.assertEquals(t.getText(), ".1.1");

        l = new ServiceLexer("abc_-1234567890.aa.");
        t = l.NAMEORNUMBER();
        Assert.assertEquals(t.getType(), ServiceToken.TOKEN_NAME);
        Assert.assertEquals(t.getText(), "abc_-1234567890.aa.");
    }

    @Test
    public void testNextToken() {
        ServiceLexer l = new ServiceLexer("s1.version=1,s2.ip='192.168.100.1', s3.version!=[1.1,'1.2']");
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_NAME);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_OP);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_NUMBER);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_COMMA);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_NAME);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_OP);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_STRING);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_COMMA);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_NAME);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_OP);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_LSBRACK);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_NUMBER);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_COMMA);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_STRING);
        Assert.assertEquals(l.nextToken().getType(), ServiceToken.TOKEN_RSBRACK);
    }
}
