# nginx 配置 导致静态资源加载失败   

### 情景 
![image](https://github.com/xx13295/wxm/blob/master/images/nginx/nginx1.png?raw=true)

	网站 使用了 nginx 做代理服务器，有时候访问页面的时候 会出现静态资源读取失败，导致网页样式乱七八糟
	
	QAQ 这样 很 丑有没有 
	
	 再次刷新 会发现网页又正常访问了
	 
	浏览网页时出现 ERR_CONTENT_LENGTH_MISMATCH 
	
	莫慌 这是由于nginx 对资源文件做了缓存处理 存在 nginx安装目录中 一个 proxy_temp 目录下
	
	
	
### 我们可以查看 nginx 错误 日志文件 如下图所示
![image](https://github.com/xx13295/wxm/blob/master/images/nginx/nginx2.png?raw=true)
	
	从图中 可 发现 权限不足问题 导致 本次访问失败
	因为 大部分 nginx 配置 基本是默认的
	所以 在nginx.conf 中 第二行 worker_processes 的前面一行 是这样的 #user nobody;
	
	
#	解决办法

	1.	首先 将 # 号去掉 将nobody 改为root 于是变成 user root; 按 Esc --> : wq 
	2.	cd 到 local 文件夹下 ，因为我的 nginx 在这个文件夹下
	3.	[blog@ojbk local]$ sudo chown -R root:root nginx
	4.	[blog@ojbk local]$ sudo chmod -R 755 nginx         
	5.	[blog@ojbk local]$ sudo chmod -R u+s nginx
	6.	[blog@ojbk conf]$ sudo nginx -s reload
	
	
理论上不可能还有问题了 如果有  那么就 sudo rm -rf /* ![image](https://github.com/xx13295/wxm/blob/master/images/o.png?raw=true)
	