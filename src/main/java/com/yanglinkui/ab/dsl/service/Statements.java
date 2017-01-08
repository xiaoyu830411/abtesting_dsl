package com.yanglinkui.ab.dsl.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jonas on 2017/1/4.
 */
public class Statements {

    private Map<String, Operation> operations = new ConcurrentHashMap<String, Operation>();

    public Statements() {}

    public Operation getOperation(String key) {
        return this.operations.get(key);
    }

    public void putOperation(String key, Operation expression) {
        this.operations.put(key, expression);
    }

    public Map<String, Operation> getOperations() {
        return this.operations;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Operation> entry : this.operations.entrySet()) {
            sb.append(entry.getValue()).append(",");
        }

        return sb.toString();
    }
}
