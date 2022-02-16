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
