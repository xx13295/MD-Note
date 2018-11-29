# Ubuntu 系统

### 查看系统版本
 
>lsb_release -a

### 查看当前安装的内核 

>dpkg -l|grep linux-image

### 安装新内核 

>sudo apt-get install linux-image-3.16.0-43-generic linux-image-extra-3.16.0-43-generic

### 卸载不要的内核 

>sudo apt-get purge linux-image-4.4.0-38-generic linux-image-extra-4.4.0-38-generic

### 更新 grub引导 

>sudo update-grub

### 搜索并更新可用的内核

>sudo apt-cache search linux-image

# CentOs 系统

### 查看CentOs版本号：

>cat /etc/redhat-release

### 查内核版本

>uname -r

### 内核版本获取 请网上自找

	这里提供的 内核都是 某速 可以用的内核

#### CentOs 6

>rpm -ivh https://static.ojbk.plus/centos-rpm/kernel-firmware-2.6.32-504.3.3.el6.noarch.rpm
>rpm -ivh https://static.ojbk.plus/centos-rpm/kernel-2.6.32-504.3.3.el6.x86_64.rpm --force

#### CentOs 7

>rpm -ivh https://static.ojbk.plus/centos-rpm/kernel-3.10.0-229.1.2.el7.x86_64.rpm --force

### 确认 我们更新的内核版本是 default 0

>cat /boot/grub/grub.conf 

通常新装都会排到前面

我们会看到`title CentOS (2.6.32-504.3.3.el6.x86_64) ` 然后一大溜 [这个是我们刚装的]
 
下面还有一个例如`title CentOS (2.6.32-754.6.3.el6.x86_64)`然后一大溜[这个是原来就带的]
 
非注释部分的 开始 有个 `default=0`

这个`default=0` 代表 选择的内核是` 2.6.32-504`

如果改成 ` default=1` 就代表 选择的内核是 `2.6.32-754`
 

### 重启

>reboot

### 启动后 看是否成功换成我们想要的内核  

>uname -r

### 查看安装了哪些 内核包

>rpm -qa |grep kernel

### 使用yum remove 或rpm -e 删除无用内核

>yum remove kernel-2.6.32-754.6.3.el6.x86_64

### 搜索并更新可用的内核 
>yum install search kernel

