# 注意事项
	
	可能不适合新手观看 哈哈 以下内容属于 随笔记录 

## 无脑docker 版

docker run  -itd -p 9000:9000 --name minio \
-e "MINIO_ACCESS_KEY=ojbk" \
-e "MINIO_SECRET_KEY=12345678" \
-v ~/docker/minio/data:/data \
-v ~/docker/minio/config:/root/.minio \
minio/minio server /data


######
export MINIO_ACCESS_KEY=admin
export MINIO_SECRET_KEY=12345678


### 二进制go编译现成版

wget https://dl.min.io/server/minio/release/linux-amd64/minio

chmod +x minio
./minio server /home/ojbk/data

nohup  ./minio server --address 0.0.0.0:33333 /home/ojbk/data > minio.log  2>&1 &




# 自己编译版

首先要有go 环境

	需要科学上网

wget https://dl.google.com/go/go1.14.5.linux-amd64.tar.gz

root账户执行 普通账户注意权限问题（毕竟usr/local 这个目录普通用户是无法操作的）

tar -C /usr/local -xzf go1.4.linux-amd64.tar.gz

cd 

加入环境变量
vi .profile 

export PATH=$PATH:/usr/local/go/bin

保存

 当然你要去https://github.com/minio/minio 下一份go的源码

	#set CGO_ENABLED=0
	set GOOS=linux
	set GOARCH=amd64
	 ###  毕竟go被墙了
	export GOPROXY=https://goproxy.io
	go build main.go



### mc 客户端


	wget https://dl.min.io/client/mc/release/linux-amd64/mc
	chmod +x mc
	
	ln -s /root/sf/mc /usr/bin/mc
	
	
	
	mc config host add minio http://ip:port QW544GDG67AHD XH584GHGSFGIK --api S3v4
	
	
	配置策略命令查看: mc policy
	
	
 设置  bucket 匿名访问 即开放权限    （image 为bucket， 前面的minio 为配置文件中的别名）
具体位置  /root/sf/.mc/config.json	

	mc  policy  set  download  minio/image


```

	{
	        "version": "9",
	        "hosts": {
	                "gcs": {
	                        "url": "https://storage.googleapis.com",
	                        "accessKey": "YOUR-ACCESS-KEY-HERE",
	                        "secretKey": "YOUR-SECRET-KEY-HERE",
	                        "api": "S3v2",
	                        "lookup": "dns"
	                },
	                "local": {
	                        "url": "http://localhost:33333",
	                        "accessKey": "*****",
	                        "secretKey": "*****",
	                        "api": "S3v4",
	                        "lookup": "auto"
	                },
	                "minio": {
	                        "url": "http://ip:port",
	                        "accessKey": "QW544GDG67AHD",
	                        "secretKey": "XH584GHGSFGIK",
	                        "api": "S3v4",
	                        "lookup": "auto"
	                },
	                "play": {
	                        "url": "https://play.min.io",
	                        "accessKey": "Q3AM3UQ867SPQQA43P2F",
	                        "secretKey": "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG",
	                        "api": "S3v4",
	                        "lookup": "auto"
	                },
	                "s3": {
	                        "url": "https://s3.amazonaws.com",
	                        "accessKey": "YOUR-ACCESS-KEY-HERE",
	                        "secretKey": "YOUR-SECRET-KEY-HERE",
	                        "api": "S3v4",
	                        "lookup": "dns"
	                }
	        }
	}

```


