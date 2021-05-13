# 规则引擎Drools 规则文件关键字 Global

### Global全局变量

    global关键字用于在规则文件中定义全局变量，
    它可以让应用程序的对象在规则文件中能够被访问。
    可以用来为规则文件提供数据或服务。

语法结构为：
    
    global 对象类型 对象名称

举例：

drl 文件

```aidl

package helloworld


global java.util.List myGlobalList //定义一个集合类型的全局变量
global java.lang.Integer count   //定义一个包装类型的全局变量
global io.springboot.drools.service.GlobalService globalService //定义一个JavaBean类型的全局变量

rule "rule_global"
    when
        eval(true)
    then
        myGlobalList.add("ojbk");
        System.err.println(count);
        globalService.get(count);
end

```

java 代码

```aidl

    @Autowired
    private GlobalService globalService;

    @Autowired
    private KieBase kieBase;

    @RequestMapping("/global")
    public String global(){
        List<String> list = new ArrayList<>();
        
        KieSession session = kieBase.newKieSession();
        
        session.setGlobal("globalService", globalService);
        session.setGlobal("count", 6);
        session.setGlobal("myGlobalList", list);
        
        session.fireAllRules();
        session.dispose();
        
        System.err.println(list);
        return "ok";
    }
        
```

```aidl

public interface GlobalService {

    void get(Integer count);
    
}


@Service
public class GlobalServiceImpl implements GlobalService {
    @Override
    public void get(Integer name) {

        System.err.println("使用 global 方式 get count: " + count);

    }
}

```

以上代码请自行在代码中加入实验 动手试试比光看强 [传送门](https://github.com/xx13295/springboot-drools-redis)

观看源码 可知 setGlobal 本质上其实是一个map

因此 drl文件中声明的global 要和java代码中的类型、变量名保持一致

MapGlobalResolver 它实现了GlobalResolver

```aidl
public class MapGlobalResolver implements GlobalResolver, Globals, Externalizable {

    private static final long serialVersionUID = 510l;

    private Map<String,Object> map;
    
    private Globals delegate;

    public MapGlobalResolver() {
        this.map = new ConcurrentHashMap<String, Object>();
    }

    public MapGlobalResolver(Map<String, Object> map) {
        if (map instanceof ConcurrentHashMap) {
            this.map = map;
        } else {
            this.map = new ConcurrentHashMap<String, Object>();
            this.map.putAll(map);
        }
    }

    public void setGlobal(String identifier, Object value) {
        this.map.put( identifier, value );
    }
    
    ······

```

注意：

    global是不会放到工作内存中的如果我们在定义全局变量时有两个规则文件中的都用到了同一个全局变量
    这两个global 的内容不会因为其他调用的改变而改变。
    因此 global 不用来做数据共享, session会影响global的用法
