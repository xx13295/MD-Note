# 增加访问控制 

	通常部分小姐姐的资源放到服务器直接爆乳在公网不太合适
	因此要是能加上账户密码访问就非常ojbk了。
	
# 静态资源如下配置
	
	监听一个23333端口
	localhost 可以改成你的静态资源访问域名 如static.xxx.com
	则浏览器访问就是 static.xxx.com:23333
	如果下面直接监听80端口 就不需要增加额外的 :80 了
	
	
	
	 
	server{
	        listen 23333;
	        server_name localhost;
	        location / {
	        		
			auth_basic      "ojbk";   #标识
			auth_basic_user_file /usr/local/nginx/conf/htpasswd; #用户名密码配置文件
	                
	                root /usr/local/static;
	                autoindex on;               # 开启索引
	                charset utf-8;              # 解决文件名称中文乱码的问题
	                autoindex_exact_size on;    # 显示文件大小
	                autoindex_localtime on;     # 显示最后修改时间
	        }
	}



## 生成密码文件

	
>cd /usr/local/nginx/conf

	使用openssl生成密码

>openssl passwd ojbk

	得到密文密码 
	
>mhbz9pgglXQT6

	配置用户密码文件 
	
>echo "admin:mhbz9pgglXQT6" > htpasswd


### 重新读取nginx 配置文件

>sudo nginx -s reload
	
	此时 再次访问  static.xxx.com:23333 
	就需要输入账户名admin 密码ojbk 才能访问到 相应的静态资源了
