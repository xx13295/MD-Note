## minio

```

docker run -itd -p 9000:9000 --name minio \
-e "MINIO_ACCESS_KEY=ojbk" \
-e "MINIO_SECRET_KEY=ojbk.plus" \
-v /disk1/dockerContainer/minio/data:/data \
-v /disk1/dockerContainer/minio/config:/root/.minio \
minio/minio server /data
```
