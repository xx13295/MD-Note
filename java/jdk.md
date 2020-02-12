### 下载文件 ![image](https://github.com/xx13295/wxm/blob/master/images/o.png?raw=true)

>wget https://static.ojbk.plus/jdk-linux-x64.tar.gz
		
		
		究极无敌慢做好心理准备，腾讯小水管233333 
		
### 上传到 /usr/local/java 下

### 解压

>tar -zxvf jdk-linux-x64.tar.gz

### 删除原文件

>rm jdk-linux-x64.tar.gz

### 修改/etc/profile文件(注意版本)

>vi /etc/profile
	
	#JDK
	export JAVA_HOME=/usr/local/java/jdk1.8.0_131
	export JRE_HOME=/usr/local/java/jdk1.8.0_131/jre
	export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools/jar:$JRE_HOME/lib:$CLASSPATH
	export PATH=$JAVA_HOME/bin/:$PATH

### 保存 Esc :wq

### 环境变量生效

>source /etc/profile
