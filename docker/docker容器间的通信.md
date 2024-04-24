# 容器之间的通信方式

容器之间的网络隔离

查看容器ip

>docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}'  容器id

    link 方式 和 network 方式

### link

    link可以通过容器名互相通信，容器间共享环境变量。

    link主要用来解决两个容器通过ip地址连接时容器ip地址会变的问题.

    --link 容器A的name:别名 

    缺点：有循环依赖问题
 
### network

   推荐使用

自己组网然后把相应的容器加入进来

> docker network create test

>docker network ls

 在原有创建容器的基础上增加 `--network test`  这里的 test 为你创建的network组
 
之后安装一个nettools检验是否可达

>docker run -itd --name nettools --network test travelping/nettools

进入容器即可ping对应容器的ip测试连通性 要注意的是 要在相同的 network 下才能相互访问