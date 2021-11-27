## minio

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
