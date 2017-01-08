package com.yanglinkui.ab.dsl.condition;

import com.yanglinkui.ab.dsl.Lexer;
import com.yanglinkui.ab.dsl.Token;

/**
 * Created by jonas on 2017/1/3.
 */
public class ConditionLexer extends Lexer {

    public ConditionLexer(String input) {
        super(input);
    }

    public Token nextToken() {
        while ( c!=EOF ) {
            switch ( c ) {
                case ',' : consume(); return new ConditionToken(ConditionToken.TOKEN_COMMA, ",");
                case '[' : consume(); return new ConditionToken(ConditionToken.TOKEN_LSBRACK, "[");
                case ']' : consume(); return new ConditionToken(ConditionToken.TOKEN_RSBRACK, "]");
                case '(' : consume(); return new ConditionToken(ConditionToken.TOKEN_LBRACK, "(");
                case ')' : consume(); return new ConditionToken(ConditionToken.TOKEN_RBRACK, ")");
                case '*' : consume(); return new ConditionToken(ConditionToken.TOKEN_STAR, "*");
                case '=' :
                case '!' :
                case '>' :
                case '<':
                    return OP();
                case '&':
                case '|':
                    return BOOL();
                case '\"':
                case '\'':
                    return STRING();
                default:
                    if (isWS(c)) {
                        WS();
                        continue;
                    }

                    if (isNAMEORNUMBER()) {
                        return NAMEORNUMBER();
                    }

                    throw new Error("Invalid character: " + c);
            }
        }
        return new ConditionToken(ConditionToken.TOKEN_EOF, "<EOF>");
    }

    Token OP() {
        StringBuilder buf = new StringBuilder(4);
        char c1 = c;
        consume();

        buf.append(c1);

        if (c1 == '!' && c == '=') {
            buf.append(c);
            consume();
        } else if (c1 == '!') {
            throw new Error("Expecting [!=] = found " + c);
        }

        if ((c1 == '>' || c1 == '<') && c == '=') {
            buf.append(c);
            consume();
        }

        return new ConditionToken(ConditionToken.TOKEN_OP, buf.toString());
    }

    Token BOOL() {
        if (c == '&') {
            match('&');
            match('&');

            return new ConditionToken(ConditionToken.TOKEN_BOOL, "&&");
        } else {
            match('|');
            match('|');

            return new ConditionToken(ConditionToken.TOKEN_BOOL, "||");
        }
    }

    Token NAMEORNUMBER() {
        StringBuilder buf = new StringBuilder();
        boolean isNumber = true;
        int dotCount = 0;
        do {
            buf.append(c);
            if (isNumber) {
                if (!(c >= '0' && c <= '9' || c == '.') || dotCount > 1) {
                    isNumber = false;
                }

                if (c == '.') {
                    dotCount++;
                }
            }

            consume();
        } while (isNAMEORNUMBER());

        if (isNumber && buf.charAt(0) != '.' && buf.charAt(buf.length() - 1) != '.') {
            return new ConditionToken(ConditionToken.TOKEN_NUMBER, buf.toString());
        } else {
            return new ConditionToken(ConditionToken.TOKEN_NAME, buf.toString());
        }
    }

    boolean isNAMEORNUMBER() {
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
        return new ConditionToken(ConditionToken.TOKEN_STRING, buf.toString());
    }

    boolean isLETTER() {
        return c>='a'&&c<='z' || c>='A'&&c<='Z';
    }

    boolean isNUMBER() {
        return c>='0'&&c<='9';
    }
}
