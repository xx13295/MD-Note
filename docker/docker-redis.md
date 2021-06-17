# docker-redis

1.拉取redis 镜像

>docker pull redis:5.0.5

2.创建 文件夹

>mkdir -p /disk1/dockerContainer/redis/data


3. 复制相应 redis.conf 文件到 /disk1/dockerContainer/redis下

4. 启动redis

```

docker run --restart=on-failure:3 --privileged=true -p 6379:6379 --name redis5.0.5 -v /disk1/dockerContainer/redis/redis.conf:/etc/redis/redis.conf -v /disk1/dockerContainer/redis/data:/data -d redis:5.0.5 redis-server /etc/redis/redis.conf --appendonly yes

```


### 后续使用

1.启动容器 

> docker start redis5.0.5

2.停止容器

>docker stop redis5.0.5
