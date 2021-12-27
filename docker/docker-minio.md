## minio

### 新版本

```
docker run -itd -p 9000:9000 -p 9001:9001 --name minio \
-e "MINIO_ROOT_USER=ojbk" \
-e "MINIO_ROOT_PASSWORD=ojbk.plus" \
-v /disk1/dockerContainer/minio/data:/data \
-v /disk1/dockerContainer/minio/config:/root/.minio \
-d minio/minio server /data --console-address ":9001"


```

### 旧版本

    minio/minio 会自动拉去最新版 所以请用下面的指定版本方式
    
    新旧版本的区别大概就是 

    Console 登录账号：MINIO_ROOT_USER替代了MINIO_SECRET
    
    Console 登录密码：MINIO_ROOT_PASSWORD替代了MINIO_SECRET_KEY

    另外一处变化则为web管理的地址和API地址已经分离，需要参数配置--console-address ":9001"


```

docker run -itd -p 9000:9000 --name minio \
-e "MINIO_ACCESS_KEY=ojbk" \
-e "MINIO_SECRET_KEY=ojbk.plus" \
-v /disk1/dockerContainer/minio/data:/data \
-v /disk1/dockerContainer/minio/config:/root/.minio \
minio/minio server /data
```


### 指定版本

```

docker pull minio/minio:RELEASE.2021-06-17T00-10-46Z
docker tag minio/minio:RELEASE.2021-06-17T00-10-46Z minio/minio:ojbk

docker run -itd -p 10128:9000 --name minio \
-e "MINIO_ACCESS_KEY=ojbk" \
-e "MINIO_SECRET_KEY=ojbk.plus" \
-v /disk1/dockerContainer/minio/data:/data \
-v /disk1/dockerContainer/minio/config:/root/.minio \
minio/minio:ojbk server /data

```
