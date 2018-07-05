
# Nginx-屏蔽指定IP	
	
>cd usr/local/nginx/conf

>mkdir deny

>cd deny

>vi ip.conf

>deny 192.168.1.1;

>Esc    ---》     :wq
	
	
	这样就是对 192.168.1.1  这个ip 的禁止访问
	再有ip 直接修改这个ip.conf 加入新的黑名单ip 就行啦。
	
	
|示例|作用|
|:-|:-|
|deny 192.168.1.1;|#过滤单个IP|
|deny 192.168.1.0/24;|#过滤整个地址段|
|deny all; |#过滤所有IP|
|allow 192.168.1.1; | #与deny all;组合是指除192.168.1.1外其他都过滤|


	
# 当然 需要 进入nginx.conf 中 include 一下这个配置 才会生效

>cd /usr/local/nginx/conf

>vi nginx.conf

加入以下这句 即可

>include usr/local/nginx/conf/deny/ip.conf; 
	
>Esc    ---》     :wq

	可以放到http, server, location, limit_except语句块
	
	一般放到 http{}标签末尾
	http{
		.....省略
		include /deny/ip.conf; 
	}
	
然后重启 就ojbk

对了。被禁止的ip会显示 403  

可以在server{} 中加入 
	
	error_page 403 /403.html;
	        location = /403.html {
	          root  html;
	          allow all;
	}  

然后在 /usr/local/nginx/html 中 mkdir 一个403.html 页面  就会跳转到这里啦

