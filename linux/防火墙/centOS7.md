#开启关闭服务

### 开启

>service firewalld start

### 重启

>service firewalld restart

### 关闭

>service firewalld stop

# 查看当前开放端口

>firewall-cmd --list-all

# 开放233端口

>firewall-cmd --permanent --add-port=233/tcp

# 移除端口

>firewall-cmd --permanent --remove-port=8080/tcp

#重启防火墙(修改配置后要重启防火墙)

>firewall-cmd --reload

# 参数解释

1、firwall-cmd：是Linux提供的操作firewall的一个工具；
2、--permanent：表示设置为持久；
3、--add-port：标识添加的端口；