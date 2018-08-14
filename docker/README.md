# Docker 
### Docker 是一个开源的应用容器引擎，基于 Go 语言 并遵从Apache2.0协议开源。
	
	1、简化程序：
	Docker 让开发者可以打包他们的应用以及依赖包到一个可移植的容器中，然后发布到任何流行的 Linux 机器上，便可以实现虚拟化。Docker改变了虚拟化的方式
	使开发者可以直接将自己的成果放入Docker中进行管理。方便快捷已经是 Docker的最大优势，过去需要用数天乃至数周的	任务，在Docker容器的处理下，只需要数秒就能完成。

	2、避免选择恐惧症：
	如果你有选择恐惧症，还是资深患者。Docker 帮你打包你的纠结！比如 Docker 镜像；Docker 镜像中包含了运行环境和配置，所以 Docker 可以简化部署多种应用实例工作。
	比如 Web 应用、后台应用、数据库应用、大数据应用比如 Hadoop 集群、消息队列等等都可以打包成一个镜像部署。

	3、节省开支：
	一方面，云计算时代到来，使开发者不必为了追求效果而配置高额的硬件，Docker 改变了高性能必然高价格的思维定势。Docker 与云的结合，让云空间得到更充分的利用。
	不仅解决了硬件管理的问题，也改变了虚拟化的方式。

	
	总之牛逼 ：

	1.速度飞快以及优雅的隔离框架
	2.物美价廉
	3.CPU/内存的低消耗
	4.快速开/关机
	5.跨云计算基础构架


----------------------------
	食用方法          
----------------------------

## 安装

>sudo yum -y install docker-io

//添加docker用户组

>sudo groupadd docker

//将登陆用户加入到docker用户组中     

>sudo gpasswd -a $USER docker 

//更新用户组    

>newgrp docker  

//测试docker命令是否可以使用sudo正常使用   

>docker ps    

//启动
>service docker start


## Hello Docker

//创建一个BusyBox，它是一个最小的Linux系统，它提供了该系统的主要功能，不包含一些与GNU相关的功能和选项。
>sudo docker pull busybox

// 运行 Hello Docker
>docker run busybox /bin/echo Hello Docker
	
//现在，让我们以后台进程的方式运行hello docker
>ojbk=$(docker run -d busybox /bin/sh -c "while true; do echo Hello Docker; sleep 2; done")

//过10s后查看一下 ojbk 打印了多少个 Hello Docker 运行docker logs $ojbk
>docker logs $ojbk

//ojbk 每2s 就会打印一次 Hello Docker 你再输入一次 docker logs $ojbk 就会发现 Hello Docker 越来越多了。
//停止后台进程方式运行方法 
>docker stop $ojbk

//重启就使用 restart 即 docker restart $ojbk
//如果要完全移除容器，需要先将该容器停止，然后才能移除。
>docker rm $ojbk

//将容器的状态保存为镜像.注意，镜像名称只能取字符[a-z]和数字[0-9]。
>docker commit $ojbk new1

//查看所有镜像的列表
>docker images

//查看所有Docker命令
>docker help

![image](http://wx1.sinaimg.cn/mw690/0060lm7Tly1ftvkcuq4kvg300u00umx0.gif)
	 
	