package com.yanglinkui.ab.dsl.action;

import com.yanglinkui.ab.dsl.Lexer;
import com.yanglinkui.ab.dsl.Token;

/**
 * Created by jonas on 2017/1/8.
 */
public class ActionLexer extends Lexer {

    public ActionLexer(String input) {
        super(input);
    }

    public Token nextToken() {
        while ( c!=EOF ) {
            switch ( c ) {
                case ',' : consume(); return new ActionToken(ActionToken.TOKEN_COMMA, ",");
                case '(' : consume(); return new ActionToken(ActionToken.TOKEN_LBRACK, "(");
                case ')' : consume(); return new ActionToken(ActionToken.TOKEN_RBRACK, ")");
                case '=' : consume(); return new ActionToken(ActionToken.TOKEN_OP, "=");
                case '\"':
                case '\'':
                    return STRING();
                default:
                    if (isWS(c)) {
                        WS();
                        continue;
                    }

                    if (isNAME()) {
                        return NAME();
                    }

                    throw new Error("Invalid character: " + c);
            }
        }
        return new ActionToken(ActionToken.TOKEN_EOF, "<EOF>");
    }

    Token NAME() {
        StringBuilder buf = new StringBuilder();
        do {
            buf.append(c);
            consume();
        } while (isNAME());

        return new ActionToken(ActionToken.TOKEN_NAME, buf.toString());

    }

    boolean isNAME() {
        return isLETTER() || isNUMBER() || c == '-' || c == '_' || c == '.';
    }

    Token STRING() {
        char c1 = c;
        consume();
        StringBuilder buf = new StringBuilder();
        do {
            buf.append(c);
            consume();
        } while (c != c1);

        match(c1);
        return new ActionToken(ActionToken.TOKEN_STRING, buf.toString());
    }

    boolean isLETTER() {
        return c>='a'&&c<='z' || c>='A'&&c<='Z';
    }

    boolean isNUMBER() {
        return c>='0'&&c<='9';
    }
}
