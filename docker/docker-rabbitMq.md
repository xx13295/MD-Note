#  docker-rabbiteMq

1.拉取rabbiteMq 镜像

>docker pull rabbitmq:3.8.14

2.创建 文件夹

>mkdir -p /disk1/dockerContainer/rabbitmq/data /disk1/dockerContainer/rabbitmq/conf /disk1/dockerContainer/rabbitmq/log


3. 编辑 vi /disk1/dockerContainer/rabbitmq/conf/rabbitmq.conf 

```aidl

#限制guest用户远程访问 true禁止 false开启
loopback_users.guest = true
#rabbitmq 端口
listeners.tcp.default = 5672
#管理界面端口
management.tcp.port = 15672
                        


```

	注意一点 
	如果你要使用 guest用户在web管理界面上创建其他用户
	loopback_users.guest可以先选择fasle
	创建完后再将其改为true


4. 启动rabbitmq

```aidl


docker run --restart=on-failure:3 --privileged=true -d --name rabbitmq3.8 -p 10133:5672 -p 10134:15672 \
-v /disk1/dockerContainer/rabbitmq/data:/var/lib/rabbitmq \
-v /disk1/dockerContainer/rabbitmq/log:/var/log/rabbitmq \
-v /disk1/dockerContainer/rabbitmq/conf:/etc/rabbitmq f83a


```

5.进入容器 启动 rabbitmq_management

>docker exec -it rabbitmq3.8 /bin/bash

启动web管理界面

>rabbitmq-plugins enable rabbitmq_management


### 后续使用

1.启动容器 

> docker start rabbitmq3.8

2.停止容器

>docker stop rabbitmq3.8
