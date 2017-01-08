package com.yanglinkui.ab.dsl;

/**
 * Created by jonas on 2017/1/5.
 */
public abstract class Parser {
    Lexer input;

    Token[] lookahead;

    int k = 2;

    int p = 0;

    public Parser(Lexer input, int k) {
        this.input = input;
        this.k = k;

        lookahead = new Token[k];

        for (int i=1; i<=k; i++) {
            consume();
        }
    }
    protected void consume() {
        lookahead[p] = input.nextToken();
        p = (p + 1) % k;
    }
    protected Token LT(int i) {
        return lookahead[(p + i - 1) % k];
    }

    protected int LA(int i) {
        return LT(i).type;
    }

    protected Token match(int x) {
        if (LA(1) == x) {
            Token t = LT(1);
            consume();

            return t;
        } else {
            throw new Error("Expecting " + getTokenName(x) + "; found "+LT(1));
        }
    }

    protected abstract String getTokenName(int type);

}
