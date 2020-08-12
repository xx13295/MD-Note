# ruby 安装 
	
	下载地址 ：
	http://www.ruby-lang.org/en/downloads/
	
### 源码安装

	 tar -xvzf ruby-2.7.0.tgz    
	
	 cd ruby-2.7.0
	
	 ./configure
	
	 make
	
	 sudo make install
	
### 自动安装

	yum install ruby
	
	
	这种方式 安装的 ruby 的版本很低 是
	
	[root@VM-4-2-centos ~]# ruby -v
	
	ruby 2.0.0p648 (2015-12-16) [x86_64-linux]
	
	
#### 因此可以升级版本

	更换阿里云镜像 
	gem sources -a http://mirrors.aliyun.com/rubygems/
	
>安装RAM
	
	RAM（Ruby Version Manager ）是一款RAM的命令行工具，可以使用RAM轻松安装
	
	管理Ruby版本。RVM包含了Ruby的版本管理和Gem库管理(gemset)
	
	gpg --keyserver hkp://keys.gnupg.net --recv-keys 409B6B1796C275462A1703113804BB82D39DC0E3 7D2BAF1CF37B13E2069D6956105BD0E739499BDB

	
	curl -sSL https://get.rvm.io | bash -s stable
	
	source /etc/profile.d/rvm.sh
	
	查看RVM版本信息，如果可以代表安装成功。

	rvm -v
	
	查看 ruby 版本
	rvm list known
	
	我们选一个 2.7 版本
	
	rvm install 2.7
	
	然后漫长等待 
	
	
	ruby -v
	
	可以发现ruby版本已经升级到 2.7 了
	
	[root@VM-4-2-centos ~]# ruby -v
	
	ruby 2.7.0p0 (2019-12-25 revision 647ee6f091) [x86_64-linux]
 	
 	
 	
 		
### 安装bundler
	
	wget https://rubygems.org/downloads/bundler-2.1.4.gem
	
	gem install --local bundler-2.1.4.gem
	
	
### 删除 
 
 	//yum remove ruby* 
 	

### 安装pg Gem时找不到libpq-fe.h头文件？

	尝试安装libpq-dev或其他相当的东西
	
	对于Ubuntu系统：
	
	sudo apt-get install libpq-dev
	
	在Red Hat Linux（RHEL）系统上：
	
	yum install postgresql-devel
	
	对于Mac Homebrew：
	
	brew install postgresql
	
	对于Mac MacPorts PostgreSQL：
	
	gem install pg -- --with-pg-config=/opt/local/lib/postgresql[version number]/bin/pg_config
	
	对于OpenSuse：

	zypper in postgresql-devel
	
	
