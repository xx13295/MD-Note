# docker-nginx

1.拉取nginx 镜像

>docker pull nginx:1.18

2.查看nginx 镜像 获取 id

>docker images 

3. 获得 c2c45d506085 取前四位即可

>docker run -d --name nginx-test -p 80:80  c2c4

4. 创建目录 dockerContainer 准备将容器里的文件拷贝出来
> mkdir -p /disk1/dockerContainer/nginx/html /disk1/dockerContainer/nginx/logs /disk1/dockerContainer/nginx/conf

5.  docker ps -a  获得容器id dfe7287a20e9 将容器里的文件拷贝到相应的目录
 
> docker cp dfe7287a20e9:/etc/nginx/nginx.conf /disk1/dockerContainer/nginx/conf

6. 把nginx-test 停了 先docker ps -a  获取id 再 kill 在 rm

7. 启动 

>docker run -d -p 10130:80 --name nginx-1.18 -v /disk1/dockerContainer/nginx/html:/usr/share/nginx/html -v /disk1/dockerContainer/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /disk1/dockerContainer/nginx/logs:/var/log/nginx c2c4


