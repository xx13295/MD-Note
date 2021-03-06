# 服务器安装 docker-nginx

需要准备 Dockerfile

>vi Dockerfile

```

FROM centos:7
MAINTAINER wxm i@ojbk.plus

#RUN	yum makecache

# 准备环境 安装 wget
#RUN yum -y update
# 安装依赖
RUN yum -y install pcre pcre-devel  
RUN yum -y install zlib zlib-devel  
RUN yum -y install openssl openssl-devel
RUN yum -y install gcc gcc-c++ autoconf automake
RUN yum -y install zlib zlib-devel openssl openssl-devel pcre-devel
RUN	yum -y install wget
RUN	yum clean all

# 下载Nginx源码包
RUN wget http://nginx.org/download/nginx-1.18.0.tar.gz
RUN	tar zxvf nginx-1.18.0.tar.gz && \
	rm -rf nginx-1.18.0.tar.gz

# 配置及编译Nginx
RUN cd nginx-1.18.0 && ./configure --prefix=/usr/local/nginx --with-http_stub_status_module --with-http_ssl_module --with-http_ssl_module --with-threads --with-http_gzip_static_module --with-http_sub_module --with-http_v2_module && make && make install

# 删除临时文件
RUN rm -rf nginx-1.18.0

# 运行Nginx
RUN /usr/local/nginx/sbin/nginx

# 暴露80 443端口
EXPOSE 80 443

# 启动Nginx
CMD ["/usr/local/nginx/sbin/nginx", "-g", "daemon off;"]



```

注意末尾有个 .    

>docker build -t dhy:nginx1.18 .

经过漫长的等待

>docker images

就可以看到 nginx 已经打包成镜像了

当然 这里要 先搞一份nginx 配置文件到 /disk1/dockerContainer/nginx/conf/目录下

然后执行

docker run --privileged=true -d -p 10130:80 -p 10223:443 --name nginx1.18 \
-v /disk1/dockerContainer/nginx/html:/usr/local/nginx/html \
-v /disk1/dockerContainer/nginx/conf/nginx.conf:/usr/local/nginx/conf/nginx.conf \
-v /disk1/dockerContainer/nginx/logs:/usr/local/nginx/logs 9413


进入 容器 

docker exec -it nginx1.18 /bin/bash

修改一下系统时间

ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
echo "Asia/Shanghai" > /etc/timezone




## nginx 资料

https://github.com/xx13295/MD-Note/tree/master/nginx

