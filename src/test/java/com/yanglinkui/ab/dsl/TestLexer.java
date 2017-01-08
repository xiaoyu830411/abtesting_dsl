package com.yanglinkui.ab.dsl;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by jonas on 2017/1/8.
 */
public class TestLexer {

    @Test
    public void testIsWS() {
        Lexer l = new Lexer("test") {
            @Override
            protected Token nextToken() {
                return null;
            }
        };

        Assert.assertTrue("It should be true. It is space", l.isWS(' '));
        Assert.assertTrue("It should be true. It is \\t", l.isWS('\t'));
        Assert.assertTrue("It should be true. It is \\r", l.isWS('\r'));
        Assert.assertTrue("It should be true. It is \\n", l.isWS('\n'));

        for (int i = 0; i < 26; i++) {
            char c = (char) ('A' + i);
            Assert.assertFalse("It is should be false. The char is " + c, l.isWS(c));
        }

    }
}
