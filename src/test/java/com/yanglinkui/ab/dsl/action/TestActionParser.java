package com.yanglinkui.ab.dsl.action;

import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by jonas on 2017/1/8.
 */
public class TestActionParser {

    @Test
    public void testActionList() {
        ActionParser parser = new ActionParser(new ActionLexer("header(\"x-service\"=\"s1.version>2.1,s2.status='test',s3.ip=['192.168.10.1','192.168.10.2']\"),param('isUser'='true')"));
        List<Action> list = parser.actionList();

        Action action = list.get(0);
        Assert.assertEquals(action.getId(), "header");
        Assert.assertEquals(action.getKey(), "x-service");
        Assert.assertEquals(action.getValue(), "s1.version>2.1,s2.status='test',s3.ip=['192.168.10.1','192.168.10.2']");

        action = list.get(1);
        Assert.assertEquals(action.getId(), "param");
        Assert.assertEquals(action.getKey(), "isUser");
        Assert.assertEquals(action.getValue(), "true");
    }
}
