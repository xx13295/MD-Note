#  docker-mysql8.0.23

1.拉取mysql 镜像

>docker pull mysql:8.0.23

2.创建 文件夹

>mkdir -p /disk1/dockerContainer/mysql/data /disk1/dockerContainer/mysql/logs /disk1/dockerContainer/mysql/conf


3. 创建 my.cnf 文件
>vi my.cnf

```

[client]

#socket = /usr/mysql/mysqld.sock
default-character-set = utf8mb4

[mysqld]
pid-file        = /var/run/mysqld/mysqld.pid
socket          = /var/run/mysqld/mysqld.sock
datadir         = /var/lib/mysql

#slow_query_log  = 1

sql_mode        = STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION

max_connections = 2000
character_set_server = utf8mb4
collation_server = utf8mb4_bin
#
secure-file-priv=
# Disabling symbolic-links is recommended to prevent assorted security risks
symbolic-links=0

# Custom config should go here
!includedir /etc/mysql/conf.d/

```

4.查看mysql  镜像 获取 id

>docker images 

5. 获得 cbe8815cbea8 取前四位即可 并启动mysql 

```

docker run --restart=on-failure:3 --privileged=true -d \
--name mysql8.0.23 \
-v /disk1/dockerContainer/mysql/conf/my.cnf:/etc/mysql/my.cnf \
-v /disk1/dockerContainer/mysql/data:/var/lib/mysql \
-v /disk1/dockerContainer/mysql/logs:/logs \
-p 10131:3306 -e MYSQL_ROOT_PASSWORD=123456 cbe8


```

6.进入容器内部
> docker exec -it mysql8.0.23 /bin/bash

7.连接mysql (第五步指定了密码为123456)并修改密码 

>mysql -u root -p
 
>mysql> use mysql

>alter user 'root'@'%' identified with mysql_native_password by '123456';

>flush privileges;

注意 ： 可将实际的使用的密码替换 123456 

8. 退出容器

>exit

### 后续使用

1.启动容器 

> docker start mysql8.0.23

2.停止容器

>docker stop mysql8.0.23
