package com.yanglinkui.ab.dsl;

import java.lang.*;

/**
 * Created by jonas on 2017/1/3.
 */
public class Equal extends Operation {

    public boolean interpret(Context context) {
        String leftValue = variable.getValue(context);
        if (leftValue == null) {
            return false;
        }

        if (this.value instanceof Number) {
            return leftValue.equals(this.value.getValue().toString());
        } else if (this.value instanceof MyString){
            return this.value.getValue().equals(leftValue);
        } else {
            for (Value value : ((Array) this.value).getValue()) {
                if (value.getValue().toString().equals(leftValue)) {
                    return true;
                }
            }

            return false;
        }
    }

    public String toString() {
        return this.variable.toString() + "=" + this.value.toString();
    }
}
