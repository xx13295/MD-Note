# redis安装

### 2022年 已全面拥抱docker 请移步docker教程查阅

    https://github.com/xx13295/MD-Note/blob/master/docker/docker-redis.md

### 下载redis

>wget http://download.redis.io/releases/redis-4.0.8.tar.gz

### 解压

>tar xzvf redis-4.0.8.tar.gz

### 安装

>cd redis-4.0.8

>make

>cd src

>make install PREFIX=/usr/local/redis

### 移动配置文件到安装目录下

>cd ../

>mkdir /usr/local/redis/etc

>mv redis.conf /usr/local/redis/etc

### 配置redis为后台启动

>vi /usr/local/redis/etc/redis.conf 


### 将redis加入到开机启动

>vi /etc/rc.local

	在里面添加内容：(开启开机自启动)

>/usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf 

### 开启redis

>/usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf 

### 常用命令　　

	启动redis

>redis-server /usr/local/redis/etc/redis.conf 

	停止redis

>pkill redis  

### 卸载redis：
	
	删除安装目录

>rm -rf /usr/local/redis 
	
	删除所有redis相关命令脚本

>rm -rf /usr/bin/redis-* 
	
 
