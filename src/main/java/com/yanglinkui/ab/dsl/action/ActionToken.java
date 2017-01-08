package com.yanglinkui.ab.dsl.action;

import com.yanglinkui.ab.dsl.Token;

/**
 * Created by jonas on 2017/1/5.
 */
public class ActionToken extends Token {

    public static final char EOF = (char) -1;

    public static final int TOKEN_EOF = 1;

    public static final int TOKEN_NAME = 2;
    public static final int TOKEN_OP = 3;
    public static final int TOKEN_LBRACK = 4;
    public static final int TOKEN_RBRACK = 5;
    public static final int TOKEN_COMMA = 6;
    public static final int TOKEN_STRING = 7;

    public static final String[] TOKEN_NAMES =
            {"n/a", "<EOF>", "NAME", "OP(=)", "LBRACK(()", "RBRACK())", "COMMA(,)","STRING('xxxx')"};


    public ActionToken(int type, String text) {
        super(type, TOKEN_NAMES[type], text);
    }


    public static String getTokenName(int type) {
        return TOKEN_NAMES[type];
    }
}
