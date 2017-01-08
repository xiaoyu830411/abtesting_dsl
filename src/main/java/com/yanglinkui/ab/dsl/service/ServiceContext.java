package com.yanglinkui.ab.dsl.service;

import com.yanglinkui.ab.dsl.Context;
import com.yanglinkui.ab.dsl.Function;

/**
 * Created by jonas on 2017/1/8.
 */
public abstract  class ServiceContext implements Context {

    public final String getValue(Function function) {
        throw new RuntimeException("Not support function");
    }
}
