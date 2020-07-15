# 注意事项
	
	可能不适合新手观看 哈哈 以下内容属于 随笔记录 

## 无脑docker 版

docker run  -itd -p 9000:9000 --name minio \
-e "MINIO_ACCESS_KEY=ojbk" \
-e "MINIO_SECRET_KEY=12345678" \
-v ~/docker/minio/data:/data \
-v ~/docker/minio/config:/root/.minio \
minio/minio server /data


wget https://dl.min.io/client/mc/release/linux-amd64/mc
chmod +x mc

ln -s /root/mc /usr/bin/mc


mc config host add minio http://47.240.22.174:9000 QW544GDG67AHD XH584GHGSFGIK --api S3v4


配置策略命令查看: mc policy

mc  policy  set  download  minio/image

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


