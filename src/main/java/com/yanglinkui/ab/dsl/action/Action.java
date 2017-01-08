package com.yanglinkui.ab.dsl.action;

/**
 * Created by jonas on 2017/1/8.
 */
public class Action {

    private final String id;

    private final String key;

    private final String value;

    public Action(String id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.id + "('" + this.key + "'='" + this.value + "')";
    }
}
