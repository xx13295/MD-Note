### 虚拟机安装

下载镜像 

https://mirrors.aliyun.com/centos/7.9.2009/isos/x86_64/?spm=a2c6h.25603864.0.0.2b87f5adRnA0hB

参考资料 https://blog.csdn.net/qq_46138492/article/details/128512782


    cd /etc/yum.repos.d/
    
    mv CentOS-Base.repo CentOS-Base.repo.back
    
    curl -o /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-7.repo
    
    yum clean all
        
    yum makecache

>vi /etc/sysconfig/network-scripts/ifcfg-ens33

````

BOOTPROTO="static"
IPADDR="192.168.0.131"
GATEWAY="192.168.0.1"
NETMASK="255.255.255.0"
DNS1="223.5.5.5"
DNS2="223.6.6.6"

````

service network restart

然后虚拟机NAT改桥接完事。