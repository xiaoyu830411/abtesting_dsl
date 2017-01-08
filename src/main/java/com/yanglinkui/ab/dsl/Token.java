package com.yanglinkui.ab.dsl;

/**
 * Created by jonas on 2017/1/3.
 */
public abstract class Token {
    protected int type;
    protected String text;
    protected String name;

    public Token(int type, String name, String text) {
        this.type=type;
        this.name = name;
        this.text=text;
    }

    public String toString() {
        return "<'" + text + "'," + name + ">";
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
