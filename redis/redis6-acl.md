# redis6 acl功能

在redis6以前，无法进行用户权限管理，只有一个auth密码验证的功能，

如果验证码通过那么就是root权限，如果我们想要禁用一些redis指令，

只能使用rename将原指令名字修改，而在redis6中引入了ACL模块，

可以定制不同用户的权限，例如：
    
    1.用户名和密码
    
    2.可以执行的指令
 
    3.可以操作的key

查看用户信息
>acl list

创建mike用户密码为123456仅增对name开头的键有+xx的权限
>acl setuser mike on >123456 ~name* +get +getbit +ttl +scan +set +info +type +ping +setbit

创建jake用户密码为123456 拥有全部权限
>acl setuser jake on >123456 +@all ~*

查看用户列表
>acl users

查看当前用户
>acl whoami

删除用户
>acl deluser username

禁用用户
>acl setuser username off

热加载 acl配置 
>acl load

以上的操作都是内存中的 没有持久化因此有acl save
保存到aclfile中。但docker 安装的可能有权限问题？
使用手动编辑即可，反正不是常用
>acl save 

随机返回一个256bit的32字节的伪随机字符串，
并将其转换为64字节的字母+数字组合字符串
>acl genpass

>acl genpass 64


## 更多 

https://redis.io/commands/acl-cat
