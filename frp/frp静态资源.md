# 使用 rfp 做静态资源

优点，可以增加用户名密码控制访问。 当然[nginx](https://github.com/xx13295/MD-Note/blob/master/nginx/nginx%E9%9D%99%E6%80%81%E8%B5%84%E6%BA%90%E5%A2%9E%E5%8A%A0%E8%AE%BF%E9%97%AE%E6%8E%A7%E5%88%B6.md)也可以。

缺点，页面丑的可以。
	
### 在原有的客户端配置增加如下配置 

	[plugin_static_file]
	type = tcp
	remote_port = 23456             			
	plugin = static_file
	plugin_local_path = /home/wxm/ojbk
	plugin_strip_prefix = static
	plugin_http_user = admin
	plugin_http_passwd = 123456
	

#### 启动客户端

>./frpc -c ./frpc.ini

#### 浏览器访问服务端ip:23456/static/

>http://x.x.x.x:23456/static/

	输入 admin /123456  即可看到 服务器路径 /home/wxm/ojbk 下的资源
	
	下载速度主要根据你公网服务器也就是frpsever的机器的带宽决定 
