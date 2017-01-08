package com.yanglinkui.ab.dsl;

/**
 * Created by jonas on 2017/1/7.
 */
public interface Context {
    public String getValue(Variable var);

    public String getValue(Function function);

}
