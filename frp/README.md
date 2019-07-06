# frp 工具的基本使用

	软件下载 https://github.com/fatedier/frp/releases
	
	这里我顺便选了一个 包frp_0.27.0_linux_amd64.tar.gz
	
## 公网服务器

	下载frp软件
	
>wget https://github.com/fatedier/frp/releases/download/v0.27.0/frp_0.27.0_linux_amd64.tar.gz

	解压

>tar zxvf frp_0.27.0_linux_amd64.tar.gz

	强迫症重命名一下
	
>mv frp_0.27.0_linux_amd64 frp-0.27.0

	进入文件夹

>cd frp-0.27.0
	
	编辑  frps.ini文件
	 
>vi frps.ini 
	
	[common]
	bind_port = 7000
	
	#log 
	log_file = ./frps.log
	# debug, info, warn, error
	log_level = info
	log_max_days = 3
	#token 务必要写的复杂一点防止被别人随便连接
	token = ojbk.frp.ssh
	
保存退出(ESC : wq)

	启动 frps：
	
>./frps -c ./frps.ini


# 内网服务器

### 前面的步骤和 上面的一样
	
	下载frp软件
	
>wget https://github.com/fatedier/frp/releases/download/v0.27.0/frp_0.27.0_linux_amd64.tar.gz

	解压

>tar zxvf frp_0.27.0_linux_amd64.tar.gz

	强迫症重命名一下
	
>mv frp_0.27.0_linux_amd64 frp-0.27.0

	进入文件夹

>cd frp-0.27.0

### 开始不一样了

	由于是客户端嘛所以就是 修改 frpc.ini文件 
	
	下面的  x.x.x.x 为你上面安装frp服务端机器的公网ip地址
	
	客户端的 [common] 中的 port 、token  要与 服务端的一致  
	
	 
>vi frpc.ini

	[common]
	server_addr = x.x.x.x
	server_port = 7000
	token = ojbk.frp.ssh
	
	[ssh]
	type = tcp
	local_ip = 127.0.0.1
	local_port = 22
	remote_port = 6000 
	
保存退出(ESC : wq)

	启动 frpc：
	
>./frpc -c ./frpc.ini

# 通过 ssh 访问内网机器 ssh -p 端口 用户@地址

	这里我登录另一台腾讯水管机 


ssh -p 6000 wxm@x.x.x.x

	发现成功 ssh 通过公网ip地址 连接上了内网服务器 
	
	这样就能在家办公了，手动狗头.jpg
	
更多功能》》  https://github.com/fatedier/frp/blob/master/README_zh.md 


