# 记录 vps 采坑流程 
	
	上周正好 黑色星期五，于是9.9美刀入手 一只传家宝 。pending 了三天 终于 开通了、中间 还因为防火墙的问题导致 ssh直接挂了。

 	开通后到手第一件事 必然就是开机装上自带的预装系统  
 	
## 接下来 开始一系列的折腾

	在 网上搜了一下 纯净版CentOS系统的 一键安装脚本[该脚本有没有问题我不清楚]
	毕竟我不懂shell编程 里面的代码也是基本看不懂，秉着一颗真诚的心应该不会有啥问题。
	
#### 准备工作 

Ubuntu

>apt-get install -y xz-utils openssl gawk coreutils file

CentOS

>yum install -y xz openssl gawk coreutils file	


#### [下载脚本]

下面的是阉割版 仅装centos 墙裂推荐。

	下载

>wget --no-check-certificate -qO CentOSNET.sh 'https://static.ojbk.plus/shell/CentOSNET.sh' && chmod a+x CentOSNET.sh
	
	默认安装

>bash CentOSNET.sh -c 6.8 -v 64 -a
	
	脚本使用 
	
	Usage:
        bash CentOSNET.sh       -c/--centos [dist-version]
                                -v/--ver [32/i386|64/amd64]
                                --ip-addr/--ip-gate/--ip-mask
                                -yum/--mirror
                                -a/-m
                                
详情请查看 作者原文[原文链接](https://moeclub.org/2018/03/26/597/)


#### 功能强大的脚本 支持 CentOS,Ubuntu,Debian

	原作者的下载链接
>wget --no-check-certificate -qO InstallNET.sh 'https://moeclub.org/attachment/LinuxShell/InstallNET.sh' && chmod a+x InstallNET.sh	
	
	小明 的下载链接

>wget --no-check-certificate -qO InstallNET.sh 'https://static.ojbk.plus/shell/InstallNET.sh' && chmod a+x InstallNET.sh	
	
我的下载链接是我安装完后，当时的 [脚本](https://static.ojbk.plus/shell/InstallNET.sh/)

{https://static.ojbk.plus/shell/InstallNET.sh} 备份了一下

原作者的链接脚本可能会实时更新的以后会出现更多的新功能也可能修复某一些bug

 当然也不能保证以后会不会加入恶意代码啥的 毕竟在网上 防人之心不可无

	 默认安装
	
>bash InstallNET.sh -c 6.9 -v 64 -a
	 
	 然后 等待 20~30分钟左右 就好了
	[链接不了不要慌张 等就完事了 过了1小时还不行 估计要重装了]
	 如果要查看进度 一般的云服务商都有vnc服务
	 使用它就可以实时查看安装进度了
	
	

#### 警告

	反正别人造好的轮子 用就完事了 用就代表相信作者 ， 
	当然也要承担一定的风险（ 比如 被加入恶意代码啥的） 。
	不相信作者就别用！


## 正式 折腾开始

   这个脚本装完系统以后 默认root账号密码如下
   
|参数|详情|
|:-|:-|
|用户|root |   
|密码|Vicer |   
|端口|22 |     

使用 ssh工具登录 

#### 一 、第一步肯定是修改密码了

>passwd 

然后输入两次新密码就完事了

#### 二 、修改ssh 端口

>vi /etc/ssh/sshd_config

按键盘的 i 键 开始 编辑
找到#Port 22
删除#号 
在另起一行写入 

>Port 2333

这样就同时拥有了 22端口和2333端口的ssh链接端口

保存退出（Esc --> :  -->  wq ）

>vi /etc/sysconfig/iptables

找到 `-A INPUT -m state --state NEW -m tcp -p tcp --dport 22 -j ACCEPT` 这一行

这这一行后面 输入`-A INPUT -m state --state NEW -m tcp -p tcp --dport 2333 -j ACCEPT`

一定要在这个后面加入 别 直接放到最后一行 ，放最后面可能会导致防火墙出现问题，到时候连不上就哭吧

然后 重启防火墙

>/etc/rc.d/init.d/iptables restart

查看添加的端口是否打开

>/etc/init.d/iptables status

重启sshd服务

>/etc/init.d/sshd restart

重启sshd之后使用新的端口测试下 登录 

如果可以登录就编辑`/etc/ssh/sshd_config` 删除`Port 22` 保存后重启sshd 服务

这样以后就可以用新的2333端口登录了

#### 三、 创建普通用户  

创建一个ojbk用户

>useradd  ojbk

为ojbk用户设置密码

>passwd ojbk

输入两遍密码就完事了

赋予这个用户sudo权限 首先先给'suders'文件修改权限

>chmod u+w /etc/sudoers

找到`root    ALL=(ALL)       ALL` 这一行在它下面一行加上

>ojbk    ALL=(ALL)       NOPASSWD: ALL

保存退出完事 

这时候 这个ojbk 用户就可以使用sudo 获取root权限了

注意 `NOPASSWD: ALL` 这个写法 是获取root权限时不必再次输入密码

当然如果你喜欢输密码 去掉前面的`NOPASSWD: ` 就完事了

#### 四、关闭root用户直接ssh登录

>/etc/ssh/sshd_config


找到这一行`#PermitRootLogin yes`

去掉 前面的`#`号 并且把`yes` 改成`no`
变成`PermitRootLogin no`
保存退出

重启 sshd 服务
>/etc/init.d/sshd restart

这时候 root 用户 就不能直接登录了。 

可以先登录普通用户 使用 `su root`切换root用户。


#### 五、 禁止 ping  
	
	ping命令常用于网络检测，确定两台主机之间是否可以通信，
	其使用的是ICMP（Internet控制报文协议），起到一定的安全作用。


系统默认是允许ICMP协议的，设置`/proc/sys/net/ipv4/icmp_echo_ignore_all`参数为1

> echo "1" > /proc/sys/net/ipv4/icmp_echo_ignore_all

检查参数是否为 1

>cat  /proc/sys/net/ipv4/icmp_echo_ignore_all

如果该参数为1，表示禁止忽略ICMP报文的所有请求，系统默认值为0，表示允许其它主机ping该主机























