package com.yanglinkui.ab.dsl.service;

/**
 * Created by jonas on 2017/1/7.
 */
public class MyString implements Value<String> {

    private final String value;

    public MyString(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "String: " + this.value;
    }
}
