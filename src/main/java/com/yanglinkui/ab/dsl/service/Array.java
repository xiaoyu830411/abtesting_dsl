package com.yanglinkui.ab.dsl.service;

import java.util.HashSet;

/**
 * Created by jonas on 2017/1/7.
 */
public class Array implements Value<HashSet<? extends Value>> {

    private final HashSet<? extends Value> value;

    public Array(HashSet<? extends Value> value) {
        this.value = value;
    }

    public HashSet<? extends Value> getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        if (this.value == null || this.value.size() == 0) {
            return "Array: []";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object value : this.value) {
            sb.append(value);
            sb.append(", ");
        }
        sb.append("]");

        return "Array: " + sb.toString();
    }
}
