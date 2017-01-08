package com.yanglinkui.ab.dsl;

import com.yanglinkui.ab.dsl.Context;
import com.yanglinkui.ab.dsl.GreaterEqual;
import com.yanglinkui.ab.dsl.Number;
import com.yanglinkui.ab.dsl.Variable;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by jonas on 2017/1/8.
 */
public class TestGreaterEqual {
    @Test
    public void testInterpretByNumber() {
        Variable var = new Variable("s1.version");
        Context context = mock(Context.class);
        when(context.getValue(var)).thenReturn("1");

        GreaterEqual g = new GreaterEqual();
        g.setVariable(var);
        g.setValue(new Number(new BigDecimal("1")));
        assertTrue("The s1.version is less than 1,  or equals 1", g.interpret(context));

        for (int i = 0; i < 1000; i++) {
            Number version = new Number(new BigDecimal(i/100F));
            g.setValue(version);
            if (i <= 100) {
                assertTrue("The s1.version is less than (or equlas) " + version.getValue(), g.interpret(context));
            } else {
                assertFalse("The s1.version greater than " + version.getValue(), g.interpret(context));
            }
        }
    }
}
