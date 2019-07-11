# 什么是FRP？

	frp全名Fast Reverse Proxy， 是一个可用于内网穿透的高性能的反向代理应用
	支持 tcp、 udp 协议，同时也为 http 和 https 应用协议提供了额外的能力，
	主要用于解决一些内网服务没有公网ip但是却需要提供外网访问的问题。
	
[正反向代理](https://github.com/xx13295/MD-Note/blob/master/frp/%E6%AD%A3%E5%90%91%E4%BB%A3%E7%90%86%E4%B8%8E%E5%8F%8D%E5%90%91%E4%BB%A3%E7%90%86.md/)

![](https://raw.githubusercontent.com/xx13295/MD-Note/master/frp/img/frpjg.png)



# 什么是内网穿透？

	简单说就是内网中的一台计算机具有自己的内部IP，外网的计算机具有公共的IP，而内部IP是无法直接通过外网来访问的，
	这就需要一种方式来将外网的IP转化为内部的合法IP来进行合法访问。


# [FRP应用基本使用](https://github.com/xx13295/MD-Note/blob/master/frp/FRP%E5%BA%94%E7%94%A8%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8.md/)


# 关于拥有公网IP的服务器
	
	可以购买云服务商的vps价格相对较便宜 
	VirMach、vultr、搬瓦工、阿里云国际轻量云等等
	
### 什么是VPS？
	
	VPS（Virtual Private Server 虚拟专用服务器）技术，将一台服务器分割成多个虚拟专享服务器的优质服务。
	实现VPS的技术分为容器技术，和虚拟化技术。在容器或虚拟机中，每个VPS都可分配独立公网IP地址、独立操作系统、
	实现不同VPS间磁盘空间、内存、CPU资源、进程和系统配置的隔离，为用户和应用程序模拟出“独占”使用计算资源的体验。
	VPS可以像独立服务器一样，重装操作系统，安装程序，单独重启服务器。
	VPS为使用者提供了管理配置的自由，可用于企业虚拟化，也可以用于IDC资源租用。
	
