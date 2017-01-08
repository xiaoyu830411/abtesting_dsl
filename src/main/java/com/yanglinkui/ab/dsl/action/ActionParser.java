package com.yanglinkui.ab.dsl.action;

import com.yanglinkui.ab.dsl.Lexer;
import com.yanglinkui.ab.dsl.Parser;
import com.yanglinkui.ab.dsl.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonas on 2017/1/8.
 */
public class ActionParser extends Parser {

    public ActionParser(Lexer input) {
        super(input, 1);
    }

    protected String getTokenName(int type) {
        return ActionToken.getTokenName(type);
    }

    public List<Action> actionList() {
        List<Action> list = new ArrayList<Action>();
        list.add(action());

        while (LA(1) == ActionToken.TOKEN_COMMA) {
            consume();
            list.add(action());
        }

        return list;
    }

    Action action() {
        String id = match(ActionToken.TOKEN_NAME).getText();
        match(ActionToken.TOKEN_LBRACK);
        String key = match(ActionToken.TOKEN_STRING).getText();
        match(ActionToken.TOKEN_OP);
        String value = match(ActionToken.TOKEN_STRING).getText();
        match(ActionToken.TOKEN_RBRACK);

        return new Action(id, key, value);
    }


    public static void main(String[] args) {
        ActionParser parser = new ActionParser(new ActionLexer("header(\"x-service\"=\"s1.version>2.1,s2.status='test',s3.ip=['192.168.10.1','192.168.10.2']\"),param('isLogin'='true')"));
        List<Action> list = parser.actionList();

        System.out.println(list.get(0));
        System.out.println(list.get(1));

    }
}
