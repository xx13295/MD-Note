#  docker-mongo5.0.6

1.拉取mongo 镜像

>docker pull mongo:5.0.6

2. 初始化启动

```

docker run --restart=on-failure:3 --privileged=true -d \
--name mongo5.0.6 \
-v /disk1/dockerContainer/mongodb/datadb:/data/db \
-v /etc/localtime:/etc/localtime \
-p 27017:27017 \
-e MONGO_INITDB_ROOT_USERNAME=admin \
-e MONGO_INITDB_ROOT_PASSWORD=qq123456 \
mongo:5.0.6



```


### 后续使用

1.启动容器

> docker start mongo5.0.6

2.停止容器

>docker stop mongo5.0.6



#### 指定配置文件

```
docker run --restart=on-failure:3 --privileged=true -d \
--name mongotest \
-v /disk1/dockerContainer/mongodbtest/datadb:/data/db \
-v /disk1/dockerContainer/mongodbtest/conf:/data/configdb \
-v /etc/localtime:/etc/localtime \
-p 27027:27027 \
mongo:5.0.6  -f /data/configdb/mongo.conf 

```

mongo.conf

后面要开启security鉴权

```
systemLog:
  destination: file
  path: /var/log/mongodb/mongod.log
  logAppend: true
storage:
  dbPath: /data/db
net:
  port: 27027
  bindIp: 0.0.0.0
#security:
  #authorization: enabled
  
# how the process runs
processManagement:
  timeZoneInfo: /usr/share/zoneinfo
```
