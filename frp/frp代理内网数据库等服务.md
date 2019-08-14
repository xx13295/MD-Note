# 数据库服务大头mysql

	其实这些服务都走tcp 也就是和 ssh 的配置如出一辙
	
### 以mysql为例子

	只需将type置为tcp
	设置本地内网mysql的端口和暴露公网的端口就可以了

	[mysql]                                           
	type = tcp
	local_ip = 127.0.0.1                            
	local_port = 3306                            
	remote_port = 33306
	
	
>[mysql] 这个标签是随便写的方便区分具体的业务，写啥都行如[ojbk] 但一定要有这个[xxx]这是规范。

	这样就能使用 你公网的ip:33306 端口去访问你的内网mysql 3306端口的数据库服务了。



#### 代理 web 

	[web]
	type = http
	local_port = 8080
	custom_domains = frp.ojbk.plus
	
