# Nginx-Gzip压缩	
	
	这个模块支持在线实时压缩输出数据流。
	经过良好的配置优化，可以大幅的提升网站的输出效率。
	gzip是常见的一个压缩算法,是大部分浏览器都支持的算法
	从HTTP请求头中可以看到浏览器支持的具体压缩算法
	比较小的文件不要压缩,特别是二进制就根本别压缩了	
	

#Nginx-开启压缩		

	常规配置

1. gzip on|off #是否开启gzip
2. gzip_buffers 32 4K| 16 8K #缓冲(压缩在内存中缓冲几块? 每块多大?)
3. gzip_comp_level [1-9] #推荐6 压缩级别(级别越高,压的越小,越浪费CPU计算资源)
4. gzip_disable #正则匹配UA 什么样的Uri不进行gzip
5. gzip_min_length 200 # 开始压缩的最小长度(再小就不要压缩了,意义不在)
6. gzip_http_version 1.0|1.1 # 开始压缩的http协议版本(可以不设置,目前几乎全是1.1协议)
7. gzip_proxied # 设置请求者代理服务器,该如何缓存内容
8. gzip_types text/plain application/xml # 对哪些类型的文件用压缩 如txt,xml,html ,css
9. gzip_vary on|off # 是否传输gzip压缩标志



## 食用方法

>vi nginx.conf
	
	找到 一个被注释的  # gzip on; 将它前面的# 去掉 
	
	然后如下配置
	
>gzip			on;
	
>gzip_buffers	32 4k;

>gzip_comp_level 6;

>gzip_min_length	100; 

>gzip_types text/css text/xml text/html application/x-javascript;

>Esc    ----->       :wq


>sudo nginx -s reload