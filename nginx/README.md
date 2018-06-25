# Nginx 安装

#### 安装前的小准备
	yum -y install gcc gcc-c++ autoconf automake
	yum -y install zlib zlib-devel openssl openssl-devel pcre-devel
	
#### nginx 下载地址 http://nginx.org/en/download.html
	//下载     网络不通 请在 windows上 下载后 用rz 上传
	wget http://nginx.org/download/nginx-1.13.9.tar.gz
	//解压
	tar zxvf nginx-1.13.9.tar.gz
	//进入nginx目录
	cd nginx-1.13.9
	//编译   安装  
	./configure --prefix=/usr/local/nginx --with-http_stub_status_module --with-http_ssl_module
	make 
	sudo make install
	
	//软链接 这样不管在哪里都可以直接使用 nginx命令不需要进入 /usr/local/nginx/sbin目录
	sudo ln -s /usr/local/nginx/sbin/nginx /usr/bin/nginx
	
# 常用命令

	sudo nginx  		         启动 
	
	sudo nginx -s reload   配置文件变化后重新加载配置文件并重启nginx服务
	
	sudo nginx -s stop     停止
	
	sudo nginx -v  显示nginx的版本号
	sudo nginx -V  显示nginx的版本号和编译信息
	sudo nginx -t  检查nginx配置文件的正确性
	sudo nginx -T  检查nginx配置文件的正确定及配置文件的详细配置内容


### 开启 http2 需要从新增加编译参数

	./configure --prefix=/usr/local/nginx --with-http_stub_status_module --with-http_ssl_module --with-http_ssl_module --with-threads --with-http_gzip_static_module --with-http_sub_module --with-http_v2_module
	
	make  
	
	sudo make install
	
	
	然后在nginx 的配置 文件
	改为	listen  443 ssl http2;
	重启 ojbk
	