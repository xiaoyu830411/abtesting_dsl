package com.yanglinkui.ab.dsl.service;

import com.yanglinkui.ab.dsl.Lexer;
import com.yanglinkui.ab.dsl.Token;

/**
 * Created by jonas on 2017/1/6.
 */
public class ServiceLexer extends Lexer {

    public ServiceLexer(String input) {
        super(input);
    }

    public Token nextToken() {
        while ( c!=EOF ) {
            switch ( c ) {
                case ',' :
                    consume(); return new ServiceToken(ServiceToken.TOKEN_COMMA, ",");
                case '[' :
                    consume(); return new ServiceToken(ServiceToken.TOKEN_LSBRACK, "[");
                case ']' :
                    consume(); return new ServiceToken(ServiceToken.TOKEN_RSBRACK, "]");
                case '=' :
                case '!' :
                case '>' :
                case '<':
                    return OP();
                case '\'':
                case '\"':
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
        return new ServiceToken(ServiceToken.TOKEN_EOF, "<EOF>");
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

        return new ServiceToken(ServiceToken.TOKEN_OP, buf.toString());
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
            return new ServiceToken(ServiceToken.TOKEN_NUMBER, buf.toString());
        } else {
            return new ServiceToken(ServiceToken.TOKEN_NAME, buf.toString());
        }
    }

    boolean isNAMEORNUMBER() {
        return isLETTER() || isNUMBER() || c == '-' || c == '_' || c == '.';
    }


    boolean isLETTER() {
        return c >= 'a' && c <= 'z' || c>='A' && c <= 'Z';
    }

    boolean isNUMBER() {
        return c >= '0' && c<= '9';
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
        return new ServiceToken(ServiceToken.TOKEN_STRING, buf.toString());
    }
}
