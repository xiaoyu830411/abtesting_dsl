package com.yanglinkui.ab.dsl.service;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by jonas on 2017/1/8.
 */
public class TestLessEqual {
    @Test
    public void testInterpretByNumber() {
        Variable var = new Variable("s1.version");
        Context context = mock(Context.class);
        when(context.getValue(var)).thenReturn("1");

        LessEqual g = new LessEqual();
        g.setLeft(var);
        g.setValue(new Number(new BigDecimal("1")));
        assertTrue("The s1.version is less than 1,  or equals 1", g.interpret(context));

        for (int i = 0; i < 1000; i++) {
            Number version = new Number(new BigDecimal(i/100F));
            g.setValue(version);
            if (i >= 100) {
                assertTrue("The s1.version is less than (or equlas) " + version.getValue(), g.interpret(context));
            } else {
                assertFalse("The s1.version is greater than " + version.getValue(), g.interpret(context));
            }
        }
    }
}
