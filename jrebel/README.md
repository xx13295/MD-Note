# 远程热部署断点调试

	Idea 远程断点调试 
	
	如果你还在用eclipse 那么接下的教程对你毫无价值。
	
### 准备工作：


	首先开启自动编译  点击 左上角 file -> setting 
			
![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/1.png?raw=true)

	接着搜索 compiler 

![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/2.png?raw=true)

	Ctrl + shift +a 调出 搜索框 输入 registry 。 鼠标点击 标黄的选项如下图

![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/3.png?raw=true)

![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/4.png?raw=true)


### 安装插件 jrebel 


	在线安装
	同其它插件安装一样，请按照以下步骤先行按照插件
	File  -> Settings...   ->  Plugins  ->  查找 Jrebel

	以下是网图：
	
![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/5.png?raw=true)

	按照成功后会提示重启，这时候重启IDEA即可。
	离线安装 

	先到官网下载：
	
>https://plugins.jetbrains.com/plugin/4441-jrebel-for-intellij

	点击get 点download 完事
	
![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/6.png?raw=true)

	点击 小齿轮 选择下面的Install Plugin from disk...  

	选择 离线下载好的 jrebel的插件 安装即可。
	
![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/7.png?raw=true)




### 安装 jrebel许可证  
	此步骤在linux服务器上执行

>wget https://raw.githubusercontent.com/xx13295/MD-Note/master/jrebel/jrebel-license.jar

>java -jar jrebel-license.jar

	这里可以使用nohup 启动，即便关闭窗口也不会导致进程立马关闭。

![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/8.png?raw=true)
 
![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/9.png?raw=true)


	我已经配置过了所以我的右上方是change  正常的情况应该是 

![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/10.png?raw=true)

![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/11.png?raw=true)
	
	以上是 关于jrebel的安装与激活使用，接下来开始重头戏。



## 切换到linux 服务器 

### 下载 jrebel 远程调试服务安装包

>wget http://dl.zeroturnaround.com/jrebel-stable-nosetup.zip

	解压
 
>unzip jrebel-stable-nosetup.zip

	进入 jrebel目录设置密码为123456789
>cd jrebel

>java -jar jrebel.jar -set-remote-password 123456789

### 激活服务端(jrebel/bin)

>cd bin

	启动脚本 ：./activate.sh （空格）认证服务地址  （空格） 邮箱 。 
	例如下方的格式

>./activate.sh http://xxx.xxx.xxx.xxx:8081/bc1fdd38-9be0-4251-a619-e14a4a6c21b91 i@ojbk.plus


	配置本地jrebel 

	File  -> setting ->JRebel ->Startup 

![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/12.png?raw=true)


	回到主界面 把下面两个打勾

![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/13.png?raw=true)


	打开idea  看到界面右上方 如下图显示 点击Edit Configurations...
	
![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/14.png?raw=true)


	点击 + 号  选择Remote 

![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/15.png?raw=true)

	可以看到 右边界面 如下图所示， 我们只需要修改Host ，Port随你心情改或者不改都行。 然后直接点击Ok 完事。

![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/16.png?raw=true)



## 接着将这个项目 打包到服务器上 

#### 远程热部署 启动

	注意修改 路径 以及后面的jar包 

>java -agentpath:/home/wxm/jrebel/lib/libjrebel64.so -Drebel.remoting_plugin=true -jar webapp.jar

#### 远程热部署+远程调试 启动

>java -agentpath:/home/wxm/jrebel/lib/libjrebel64.so - agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Drebel.remoting_plugin=true -jar webapp.jar

	指定端口 失效的话就是项目内部的默认配置的端口 或者使用 --server.port=8083

>java -agentpath:/home/wxm/jrebel/lib/libjrebel64.so -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Drebel.remoting_plugin=true -jar webapp.jar -port 8083


	回到 idea添加远程调试地址 以及刚才启动activate.sh设置的密码
	
![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/17.png?raw=true)

	启动远程断点服务
	
![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/18.png?raw=true)
	
	这样就可以在本地代码中打断点 做线上调试了。 修改代码等编译后点击
	
![image](https://github.com/xx13295/MD-Note/blob/master/jrebel/jrebel-pic/19.png?raw=true)	
	
	就可以热部署到线上 无需重新打包。