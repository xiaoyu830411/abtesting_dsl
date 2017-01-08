# abtesting_dsl
好久没有写解析器，写的我尴尬症都犯了

主要是定义一些ab testing表达式的一些语法，这个项目包括三部分：
* condition 前置条件解析(例如 client.ip=['192.168.10.2'] || user.id=['876'])。
* action 当满足前置条件后，要做的动作(header('x_service'='s1.version=5.1'))，这个值一般都要和后面service的配对使用。
* service 解析abtesting传过来的表达式（一般和action内容配对）(s1.version=5.1)。


condition 解析器
=============

* 支持bool操作符 &&, || ；可以通过小括号，无限嵌套
* 左边可以支持函数，例如: lastChar(client.ip) = ['2','3'] ；
* 操作符 >, >=, <, <= 只能操作数字（可以是小数)
* Context 这个类要重点说一下，我们必须自己实现一个，主要是返回操作符左边的值（变量，例如client.ip)

* 使用
```java
//如果想代表所有的条件通杀，那么就是 * 就可以了
//String condition = "*";
String condition = "mod(user.id)>500 && (client.ip='192.168.10.1' || client.browser!=['safari'])"

try {
    //解析
    ConditionParser p = new ConditionParser(new ConditionLexer(condition));
    Statements s = p.statements();

    //获得操作
    boolean result = s.interpret(new Context() {
        //实现这个类，返回变量mod函数, client.ip, client.browser的值
        public String getValue(Variable var) {
            if (var.getId().equals("client.ip")) {
                return "192.168.10.1";
            }

            if (var.getId().equals("client.browser")) {
                return "safari";
            }

            return null;
        }

        public String getValue(Function function) {
            return "501";
        }
    })
} catch (Error e) {
    throw new Error("解析出错了", e);
}

```


action 解析器(太简单了)
=============

* 使用
```java
//假如表达式是 header("x-service"="s1.version>2.1,s2.status='test',s3.ip=['192.168.10.1','192.168.10.2']"), param('isUser'='true')

try {
    //解析
    ActionParser parser = new ActionParser(new ActionLexer("header(\"x-service\"=\"s1.version>2.1,s2.status='test',s3.ip=['192.168.10.1','192.168.10.2']\"),param('isUser'='true')"));
    List<Action> list = parser.actionList();
    for (Action action : list) {
        if (action.getId.equals("header")) {
            request.addHeader(action.getKey(), action.getValue());
        } else if (action.getId.equals("param")) {
            url = url + "&" + action.getKey + "=" + action.getValue();
        }
    }


} catch (Error e) {
    throw new Error("解析出错了", e);
}

```

service 解析器
=============

* ServiceContext 这个类要重点说一下，我们必须自己实现一个，主要是返回操作符左边的值（变量，例如s1.version)
* 操作符 >, >=, <, <= 只能操作数字（可以是小数)

* 使用
```java
//从request里面获得表达式
//假如表达式是 s1.version = [1, 2.1, 3], s2.ip != ['192.168.10.1']
String serviceHeader = request.getHeader("x_service");
if (serviceHeader == null) {
    return;
}

try {
    //解析
    ServiceParser p = new ServiceParser(new ServiceLexer(serviceHeader));
    Statements s = p.statements();
    
    //获得操作
    Operation op = s.getOperation("s1.version");
    boolean result = op.interpret(new ServiceContext() {
        public String getValue(Variable var) {
            //通过自己的实现类返回 s1.version的值
        }
    })
} catch (Error e) {
    throw new Error("解析出错了", e);
}

```


