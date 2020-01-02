### 1. 下载Erlang的rpm包
	
	RabbitMQ是Erlang语言编写，所以Erang环境必须要有，
	注：Erlang环境一定要与RabbitMQ版本匹配
	
'https://www.rabbitmq.com/which-erlang.html'

	Erlang下载地址：(https://www.rabbitmq.com/releases/erlang/)
	
'https://www.rabbitmq.com/releases/erlang/erlang-18.3.4.4-1.el7.centos.x86_64.rpm'

### 2.下载RabbitMQ的rpm包

	RabbitMQ下载地址：(https://www.rabbitmq.com/releases/rabbitmq-server/)

'https://www.rabbitmq.com/releases/rabbitmq-server/v3.6.5/rabbitmq-server-3.6.5-1.noarch.rpm'	
	
### 3.下载socat的rpm包

	rabbitmq安装依赖于socat，所以需要下载socat。

	socat下载地址：(http://repo.iotti.biz/CentOS/)
	
'http://repo.iotti.biz/CentOS/6/x86_64/socat-1.7.3.2-1.el6.lux.x86_64.rpm'

	

### 4.分别安装Erlang、Socat、RabbitMQ 

	要按照顺序安装否则容易 安装失败。

>erlang-18.3.4-1.el7.centos.x86_64.rpm

>rpm -ivh socat-1.7.3.2-1.el6.lux.x86_64.rpm

>rpm -ivh rabbitmq-server-3.6.5-1.noarch.rpm 

### 5.修改 rabbitmq 配置 

>vi /usr/lib/rabbitmq/lib/rabbitmq_server-3.6.5/ebin/rabbit.app

	找到 关键词 loopback_users 将里面 的<<"guest">> 删除  。
	变为 {loopback_users, []},然后重启服务
	
	这么做的目的是 可以让guest 账号远程登录， 如果不需要 可不操作。

### 6.安装管理插件：

>rabbitmq-plugins enable rabbitmq_management


	启动RabbitMQ
	
>cd /usr/lib/rabbitmq/bin
	
>./rabbitmq-server start
	
	浏览器访问：
	
'http://XXX.XXX.XXX.XXX:15672/' 
	
	可以看到RabbitMQ的管理界面。
	
	用户密码管理的操作我们都可以在管理页面中设置。
	
### 7.默认端口

	client端   端口： 5672
	管理界面 端口： 15672
	server间内部通信 端口：25672
	erlang发现 端口：4369
	##https://github.com/rabbitmq/rabbitmq-server/blob/master/docs/rabbitmq.conf.example
	
	
>cp /usr/share/doc/rabbitmq-server-3.6.5/rabbitmq.config.example /etc/rabbitmq/

>mv rabbitmq.config.example rabbitmq.config

>vi /etc/rabbitmq/rabbitmq.config
