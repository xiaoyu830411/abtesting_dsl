package com.yanglinkui.ab.dsl;

/**
 * Created by jonas on 2017/1/4.
 */
public class Variable {

    protected String id;

    public Variable(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getValue(Context context) {
        return context.getValue(this);
    }

    public String toString() {
        return this.id;
    }

}
