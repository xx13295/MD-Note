# Mysql 8 安装

### Mysql 8 下载地址 https://dev.mysql.com/downloads/repo/yum/
	
	//下载     网络不通 请在 windows上 下载后 用rz 上传
	wget https://dev.mysql.com/get/mysql80-community-release-el7-1.noarch.rpm
	
	//安装 yum repo文件并更新 yum 缓存
	rpm -ivh mysql80-community-release-el7-1.noarch.rpm
	
	yum clean all
	
	yum makecache
	
	// 安装
	yum install mysql-community-server
	
	//启动前 
	vi /etc/my.cnf
	
	将#default-authentication-plugin=mysql_native_password  前面的# 去掉 保存 
	
	//启动
	systemctl start mysqld.service
	
	（查看  systemctl status mysqld ）
	
### 配置账户
	
	//查看root 用户 初始密码
	cat /var/log/mysqld.log | grep password

	//下面是打印结果

>2018-07-31T11:12:14.924719Z 5 [Note] MY-010454 [Server] A temporary password is generated for root@localhost: eoQA=_Qyj9=M

	//可以看出 密码为  eoQA=_Qyj9=M
	//好了 我们用这个密码 登录 root 用户 并修改 他的初始密码 
	
	mysql -u root -p 
	
	ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Mysql8ojbk666!';
	
	注意你的密码复杂度要够！ 不然修改不成功，大小写加数字加字符什么的完事了！
	
	创建普通用户
	CREATE USER 'ojbk'@'%' IDENTIFIED BY 'Ojbkmima666!';
	
	//创建一个名为  test 数据库 并分配给这个普通用户

	CREATE DATABASE test;
	GRANT SELECT,INSERT,UPDATE,DELETE ON test.* TO 'ojbk'@'%';
	
	//如果要 指定字符集  更多字符集 自己替换  不加则自动 创建 字符集utf8mb4 排序规则utf8mb4_0900_ai_ci
	CREATE DATABASE test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
	
	
	//修改  可以用 navicat 可以连接的密码方式   也可以在 创建的时候 直接 加上  [ WITH mysql_native_password ]
	ALTER USER 'ojbk'@'%' IDENTIFIED WITH mysql_native_password BY 'Ojbkmima666!';
	
	【	
		（如果要增加权限）
		 
		//all：所有权限,这里有select,update等等权限,需要什么自己 加就完事了熬~
		//后面的*.*：指定数据库.指定表，这里是所有  ，具体哪个库自己指定就完事了熬 ~
		// to 后面就是你刚才创建的用户及连接域
		
		GRANT all ON *.* TO 'ojbk'@'%';
		用以上命令授权的用户不能给其它用户授权,如果想让该用户可以授权,用以下命令: 
		GRANT all ON databasename.tablename TO 'username'@'host' WITH GRANT OPTION;
	】
	
### 常用命令

	
	//登录mysql
	mysql -u username -p
	 
	//退出mysql 
	quit
	 
	//启动mysql
	systemctl start mysqld.service
	 
	//关闭
	systemctl stop mysqld.service
	 
	//重启
	systemctl restart mysqld.service
	 
	//开机自启
	systemctl enable mysqld.service
	 
	//查看mysql版本
	mysql -V
	连上了MySQL服务器就 select version();
	
	//在执行命令时候出现警告 可查看警告
	show warnings;