# docker-redis

## redis 6.2.6

请参照下面的5.0.5 步骤
 
```
docker run --restart=on-failure:3 --privileged=true -d \
--name redis6.2.6 \
-v /disk1/dockerContainer/redis/redis.conf:/etc/redis/redis.conf \
-v /disk1/dockerContainer/redis/data:/data \
-v /etc/localtime:/etc/localtime \
-p 6379:6379 redis:6.2.6 redis-server /etc/redis/redis.conf --appendonly yes

```



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



## 进阶


配置文件注意修改 aclfile

>touch /disk1/dockerContainer/redis/users.acl


配置文件注意修改 logfile

>touch /disk1/dockerContainer/redis/redis.log

    目前涉及到容器内部权限问题 暂未得到优雅的解决方案
    --privileged=true 并未对 redis.conf中的引用文件获得权限
    无脑先进容器授权可解决redis.log读取文件。
    但问题 acl save 无法良好支持
    可手动编辑users.acl文件来使用acl功能。



```
docker run --restart=on-failure:3 --privileged=true -d \
--name redis6.2.6 \
-v /disk1/dockerContainer/redis/redis.conf:/etc/redis/redis.conf \
-v /disk1/dockerContainer/redis/users.acl:/etc/redis/users.acl \
-v /disk1/dockerContainer/redis/redis.log:/etc/redis/redis.log \
-v /disk1/dockerContainer/redis/data:/data \
-v /etc/localtime:/etc/localtime \
-p 6379:6379 redis:6.2.6 redis-server /etc/redis/redis.conf  --appendonly yes

```
