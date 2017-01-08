package com.yanglinkui.ab.dsl.service;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.mockito.Mockito.*;
import static junit.framework.Assert.*;

/**
 * Created by jonas on 2017/1/8.
 */
public class TestEqual {

    @Test
    public void testInterpretByNumber() {
        Variable s1Version = new Variable("s1.version");
        Context s1VersionContext = mock(Context.class);
        when(s1VersionContext.getValue(s1Version)).thenReturn("1.2");

        Equal equal = new Equal();
        equal.setLeft(s1Version);
        equal.setValue(new Number(new BigDecimal("1")));
        assertFalse("The s1.version number equals 1", equal.interpret(s1VersionContext));

        equal.setValue(new Number(new BigDecimal("1.2")));
        assertTrue("The s1.version number doesn't equal 1.2", equal.interpret(s1VersionContext));
    }

    @Test
    public void testInterpretByString() {
        Variable var = new Variable("s1.ip");
        Context s1Ip = mock(Context.class);
        when(s1Ip.getValue(var)).thenReturn("192.168.10.1");

        Equal equal = new Equal();
        equal.setLeft(var);
        equal.setValue(new MyString("192.168.1.1"));
        assertFalse("The s1.ip (string) equals 192.168.1.1", equal.interpret(s1Ip));

        equal.setValue(new MyString("192.168.10.1"));
        assertTrue("The s1.ip (string) equals 192.168.10.1", equal.interpret(s1Ip));
    }

    @Test
    public void testInterpretByArray() {
        Variable var = new Variable("s1.ip");
        Context s1Ip = mock(Context.class);
        when(s1Ip.getValue(var)).thenReturn("192.168.10.1");

        Equal equal = new Equal();
        equal.setLeft(var);
        HashSet<Value> list = new HashSet<Value>();
        list.add(new Number(new BigDecimal("4")));
        list.add(new MyString("192.168.1.1"));
        equal.setValue(new Array(list));
        assertFalse("The s1.ip (string) equals 192.168.1.1", equal.interpret(s1Ip));

        list = new HashSet<Value>();
        list.add(new Number(new BigDecimal("4")));
        list.add(new MyString("192.168.1.1"));
        list.add(new MyString("192.168.10.1"));
        equal.setValue(new Array(list));
        assertTrue("The s1.ip (string) equals 192.168.10.1", equal.interpret(s1Ip));
    }
}
