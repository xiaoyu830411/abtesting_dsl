package com.yanglinkui.ab.dsl.service;

import com.yanglinkui.ab.dsl.*;
import com.yanglinkui.ab.dsl.Number;
import junit.framework.Assert;
import org.junit.Test;

import java.lang.*;
import java.math.BigDecimal;

/**
 * Created by jonas on 2017/1/8.
 */
public class TestServiceParser {

    @Test
    public void testStatements() {
        ServiceLexer l = new ServiceLexer("s1.version=1,s2.ip='192.168.100.1', s3.version!=[1.1,'1.2']");
        ServiceParser p = new ServiceParser(l);

        Statements s = p.statements();
        Assert.assertNotNull(s);

        Operation e = null;
        e = s.getOperation("s1.version");
        Assert.assertEquals(e.getVariable().getId(), "s1.version");
        Assert.assertEquals(e.getValue().getValue(), new BigDecimal("1"));

        e = s.getOperation("s2.ip");
        Assert.assertEquals(e.getVariable().getId(), "s2.ip");
        Assert.assertEquals(e.getValue().getValue(), "192.168.100.1");

        e = s.getOperation("s3.version");
        Assert.assertEquals(e.getVariable().getId(), "s3.version");
        Assert.assertEquals(e.getValue().getClass(), Array.class);
        Assert.assertEquals(((Array)e.getValue()).getValue().size(), 2);

        for (Value value : ((Array)e.getValue()).getValue()) {
            if (value instanceof Number) {
                Assert.assertEquals(((Number)value).getValue(), new BigDecimal("1.1"));
            } else {
                Assert.assertEquals(((MyString)value).getValue(), "1.2");
            }
        }
    }

    @Test
    public void testDuplicateVar() {
        ServiceLexer l = new ServiceLexer("s1.version=1,s2.ip='192.168.100.1', s1.version!=[1.1,'1.2']");
        ServiceParser p = new ServiceParser(l);

        try {
            Statements s = p.statements();
            Assert.fail("The variable duplication checking does not work");
        } catch (Error e) {}

    }

    @Test
    public void testOperationForNumber() {
        ServiceLexer l = null;
        ServiceParser p = null;
        Statements s = null;
        l = new ServiceLexer("s1.version='1'");
        p = new ServiceParser(l);
        try {
            s = p.statements();
        } catch (Error e) {
            Assert.fail("The operation(=) does not support string.");
        }

        l = new ServiceLexer("s1.version>'1'");
        p = new ServiceParser(l);
        try {
            s = p.statements();
            Assert.fail("The operation(>) just support number, but it does not work.");
        } catch (Error e) {
        }

        l = new ServiceLexer("s1.version>='1'");
        p = new ServiceParser(l);
        try {
            s = p.statements();
            Assert.fail("The operation(>=) just support number, but it does not work.");
        } catch (Error e) {
        }

        l = new ServiceLexer("s1.version<'1'");
        p = new ServiceParser(l);
        try {
            s = p.statements();
            Assert.fail("The operation(<) just support number, but it does not work.");
        } catch (Error e) {
        }

        l = new ServiceLexer("s1.version<='1'");
        p = new ServiceParser(l);
        try {
            s = p.statements();
            Assert.fail("The operation(<=) just support number, but it does not work.");
        } catch (Error e) {
        }
    }

}
