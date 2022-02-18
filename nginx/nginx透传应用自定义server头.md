# nginx透传应用自定义server头

#### 举例 ：

    Springboot应用

在配置文件中加入
>server.server-header=PHP/9.9.8
>  
>


nginx 配置 增加 `proxy_pass_header Server;` 即可
```
server {
		listen       8089;
        server_name  localhost;
		
		location / {
			proxy_set_header HOST $host;
			proxy_set_header X-Forwarded-Proto $scheme;
			proxy_set_header X-Real-IP $remote_addr;
			proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

			proxy_pass_header Server;
			
			proxy_pass http://127.0.0.1:8080;
			proxy_connect_timeout 600;
			proxy_read_timeout 600;
		}
	}


```

这样别人使用f12查看到的 server 就不再是nginx了 而是php

毕竟 php 是世界上最好的语言~
