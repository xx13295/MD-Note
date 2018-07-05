# 搞事搞事搞事：
	如今我们喜欢网上冲浪，要学会隐藏自己 ，使用nginx做代理服务器，我们访问网页按F12在network中可以查看相应的资源的response返回的header中存在 server：nginx/版本号
	 这个东西被黑客看到的话，基本上可以根据指定的版本寻找攻击了.所以我们要隐藏版本号或者返回一个自定义的server给客户端达到隐藏自己的效果.

 

## 隐藏版本号:
	这个比较简单
>vi nginx.conf

	在nginx的 http{} 里加上 server_tokens off;

	http {
	#……省略配置
	server_tokens off;  #即可隐藏版本号
	#……省略配置
	}

	
	重启nginx 这时我们再看 Server 发现已经没有版本信息了，而且所有的错误页面 都只有nginx 没有显示多余的版本号
 

##  自定义的server 骚操作

	如果你已经编译安装过了 请先删除掉  因为重新编译 如果文件已经存在  貌似是不会覆盖安装的。
	这个坑已经帮你踩过啦。 
	我装这个地方  /usr/local/nginx  ，于是 我将这个nginx 删除了。 当然删除前 你先保存一下你 的配置文件 ！！ 这很重要不然 一会你还得重新配。
	
### 修改  src/http/ngx_http_header_filter_module.c 

	static char ngx_http_server_string[] = "Server: nginx" CRLF;
	
	把其中的nginx改成 自己喜欢的都可以 我改成了 OJBK . 于是就变成了Server:OJBK.   这里配合 上面的隐藏版本号食用


### 修改 src/core/nginx.h 

	#define NGINX_VERSION      "1.13.9"
	#define NGINX_VER          "nginx/" NGINX_VERSION
	#define NGINX_VAR          "NGINX"

	我们把NGINX_VERSION大小定义为6.6.6
	nginx与NGINX改为 OJBK 
	
	保存一下 。然后 cd ..  再cd ..   从新编译安装一下  再把之前的配置文件拷贝进去  启动nginx 就可以 愉快的网上冲浪了。
   
   
###  噢对了 这个时候 还得修改一下 /usr/local/nginx/html 中 html 文件 达到完美的效果 
   