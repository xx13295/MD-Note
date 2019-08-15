# 限制cpu占有率过高

	安装cpulimit

## 安装

#### 方式一 

>wget http://downloads.sourceforge.net/cpulimit/cpulimit-1.1.tar.gz

>tar -zxvf cpulimit-1.1.tar.gz

>cd cpulimit-1.1

>make

>cp cpulimit  /usr/local/sbin/

#### 方式二 
	Debian / Ubuntu

>sudo apt-get install cpulimit

	Centos

>sudo yum install cpulimit


## 如何使用cpulimit?

>cd  /usr/local/sbin/
	
	例如限制ffmpeg cpu利用率

>sudo nohup ./cpulimit -e ffmpeg -l 65 1>/dev/null 2>&1 & 

	限制进程号30519的程序cpu使用率为75%

>sudo nohup ./cpulimit -p 30519 -l 75 1>/dev/null 2>&1 & 

	可以使用绝对路径限制进程的cpu利用率

>cpulimit -P /usr/local/redis/bin/redis-server -l 50


考察资料 ：https://scoutapm.com/blog/restricting-process-cpu-usage-using-nice-cpulimit-and-cgroups
