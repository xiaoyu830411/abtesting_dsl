package com.yanglinkui.ab.dsl.condition;

import com.yanglinkui.ab.dsl.Token;

/**
 * Created by jonas on 2017/1/5.
 */
public class ConditionToken extends Token {

    public static final char EOF = (char) -1;

    public static final int TOKEN_EOF = 1;

    public static final int TOKEN_NAME = 2;
    public static final int TOKEN_OP = 3;
    public static final int TOKEN_LSBRACK = 4;
    public static final int TOKEN_RSBRACK = 5;
    public static final int TOKEN_COMMA = 6;
    public static final int TOKEN_BOOL = 7;
    public static final int TOKEN_LBRACK = 8;
    public static final int TOKEN_RBRACK = 9;
    public static final int TOKEN_STRING = 10;
    public static final int TOKEN_NUMBER = 11;
    public static final int TOKEN_STAR = 12;

    public static final String[] TOKEN_NAMES =
            {"n/a", "<EOF>", "NAME", "OP(=,!=,>,<,>=,<=)", "LSBRACK([)", "RBRACK(])", "COMMA(,)", "BOOL(&&,||)", "LBRACK(()", "RBRACK())", "STRING('xxxx')", "NUMBER('1.2')", "*"};


    public ConditionToken(int type, String text) {
        super(type, TOKEN_NAMES[type], text);
    }


    public static String getTokenName(int type) {
        return TOKEN_NAMES[type];
    }
}
