# frp管理脚本

	每次启动 ./frps -c frps.ini 关闭 ps -ef|grep frps 再去kill
	稍微有点麻烦 
	
于是编写了这个 [脚本](https://github.com/xx13295/MD-Note/blob/master/frp/frp.sh/)


>wget https://raw.githubusercontent.com/xx13295/MD-Note/master/frp/frp.sh

>chmod 755 frp.sh


可用参数 {start|stop|restart|status}

	启动

>frp.sh start

	停止

>frp.sh stop 
	
	
	
## 注意

启动前 请修改脚本 的以下参数 改为你的frp存放文件夹

	
	APP_HOME=/home/wxm/frp-0.27.0
	


当前脚本 是服务端脚本，客户端脚本也是一样的，只需要将脚本中 的
APP_MAIN和APP_CFG改成客户端的即可如下：


	APP_MAIN=frpc
	APP_CFG=frpc.ini