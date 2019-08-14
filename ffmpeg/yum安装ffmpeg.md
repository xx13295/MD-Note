# yum 安装更加方便 无需 自己安装各种编解码插件

### 1.升级系统

	sudo yum install epel-release -y
	sudo yum update -y

### 2.安装Nux Dextop Yum 源

	由于CentOS没有官方FFmpeg rpm软件包。但是，我们可以使用第三方YUM源（Nux Dextop）完成此工作。 http://li.nux.ro

>sudo rpm --import http://li.nux.ro/download/nux/RPM-GPG-KEY-nux.ro
 	
	CentOS 7

>sudo rpm -Uvh http://li.nux.ro/download/nux/dextop/el7/x86_64/nux-dextop-release-0-5.el7.nux.noarch.rpm
	
	CentOS 6

>sudo rpm -Uvh http://li.nux.ro/download/nux/dextop/el6/x86_64/nux-dextop-release-0-2.el6.nux.noarch.rpm

### 3.安装FFmpeg 和 FFmpeg开发包

>sudo yum install ffmpeg ffmpeg-devel -y

### 4.测试是否安装成功

>ffmpeg

若出现 一大溜  说明安装成功

若出现-bash: ffmpeg: command not found  说安装失败了

 

### 5. 如果出现安装失败 
	
>sudo yum clean
	然后重复上述步骤完事