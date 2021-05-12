# 规则引擎 Drools

## 1.规则引擎概述

    规则引擎，全称为业务规则管理系统，英文名为BRMS(即Business Rule Management System)。
    规则引擎的主要思想是将应用程序中的业务决策部分分离出来，并使用预定义的语义模块编写业务决策（业务规则），
    由用户或开发者在需要时进行配置、管理。
    
    需要注意的是规则引擎并不是一个具体的技术框架，而是指的一类系统，即业务规则管理系统。
    目前市面上具体的规则引擎产品有：drools、VisualRules、iLog等。
    
    规则引擎实现了将业务决策从应用程序代码中分离出来，接收数据输入，解释业务规则，并根据业务规则做出业务决策。
    规则引擎其实就是一个输入输出平台。

## 2. 使用规则引擎的优势
    使用规则引擎的优势如下：
    
    1、业务规则与系统代码分离，实现业务规则的集中管理
    
    2、在不重启服务的情况下可随时对业务规则进行扩展和维护
    
    3、可以动态修改业务规则，从而快速响应需求变更
    
    4、规则引擎是相对独立的，只关心业务规则，使得业务分析人员也可以参与编辑、维护系统的业务规则
    
    5、减少了硬编码业务规则的成本和风险
    
    6、使用规则引擎提供的规则编辑工具，使复杂的业务规则实现变得的简单

### 三、简单介绍

1、术语解释

* Rule：一条规则可以看作是if else 一组判断和一组输出

* RuleBase: RuleBase包含一个或多个规则包，它们已经被校验和编译完成，是可以序列化的

* Package: 规则包，是规则以及其它相关结构的一个集合，包必须有一个名称空间，并且使用标准的java约定进行命名

* WorkingMemory: 用户工作区，包含用户的数据和相关Rule的引用

* Facts: Facts就是规则中用到的输入，Facts可以是任何规则可以存取的Java对象,规则引擎完全不会克隆对象，它仅仅是保存对对象的一个引用/指针

2、规则文件详解

    目前市面上常用的drools规则文件通常是以  .drl 扩展名结尾 
    
    在一个drl文件中可以包含多个规则，函数等等。

规则文件的构成：

    package package-name //定义包名
    
    imports   //导入java包
    
    globals   //定义全局变量，如 global java.util.List myGlobalList
    
    functions  //定义函数
    
    rules   //一系列的规则

    querys	//一系列查询

规则的构成:

    package "packageName"
    
    imports com.xxx.Xxx
    
    rule "ruleName"
        attributes
    when
        LHS
    then
        RHS
    end

查询的构成

    query "queryName"
    
    LHS
    
    end


说明：

    LHS是规则的条件部分，可以定义变量

    RHS是允许Java语义代码，RHS中的多条语句实质上是一个规则，只有满足全部语句才符合规则

    任何在LHS中绑定的变量可以在RHS中使用    

3、规则文件示例解读

```aidl

package helloworld

import io.springboot.drools.model.Employee

rule "rule_employee_raise"
    agenda-group "group-rule2"
    no-loop true
    when
        $employee:Employee(year >= 3 && salary <= 10000)
    then
        System.out.println("抠门公司准备给员工ID="+$employee.getId()+" ,名字= "+$employee.getName()+" 加薪");
        $employee.setSalary($employee.getSalary() + 100);
        update($employee);

end

```

以上规则文件drl 可以看出 符合我们的规则构成

其中 :

attributes 为以下内容
```aidl

agenda-group "name"  
no-loop true 

```

LHS条件部分

当传入的员工对象 $employee 的工作年限year 大于等于3年 并且 工资salary小于等于10000时 触发我们的规则

```aidl
$employee:Employee(year >= 3 && salary <= 10000)
```


RHS部分可以执行相应的动作

满足条件的员工可以增加薪水100

其中 `update($employee)` 的作用是更新工作内存中的数据，并让相关的规则重新匹配。 （要避免死循环）

所以再attributes中 添加了 `no-loop true`

如果员工的工资原来为8000 执行了这个规则 没有添加 `no-loop true` 那么会导致循环加薪直到 工资变成10100

这是老板不想看到的

```aidl
 $employee.setSalary($employee.getSalary() + 100);
 update($employee);
```

划重点：

    规则引擎完全不会克隆对象，它仅仅是保存对对象的一个引用/指针
    即，在规则定义中对fact的修改，就是对代码中fact对象的修改。
    也即，规则的根本目的是产生一个供使用的输出结果，即修改后的JavaBean


### 学习资料

[完整代码传送门](https://github.com/xx13295/springboot-drools-redis)