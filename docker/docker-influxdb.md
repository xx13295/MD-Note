# docker 安装 influxdb

### 安装

此方式 环境变量有效 但要进入 容器 docker exec -it influxdb /bin/bash

cd /etc/influxdb 目录才能去修改配置文件
 
```
docker run --name influxdb --restart=on-failure:3 --privileged=true -d  -p 11034:8086 \
-e INFLUXDB_DB=test \
-e INFLUXDB_ADMIN_USER=admin -e INFLUXDB_ADMIN_PASSWORD=admin \
-e INFLUXDB_USER=root -e INFLUXDB_USER_PASSWORD=root \
-v /disk1/dockerContainer/influxdb:/var/lib/influxdb \
influxdb:1.8.2


```


### 安装

配置了 配置文件映射目录然而 -e的环境变量失效了？ 

```

docker run --name influxdb --restart=on-failure:3 --privileged=true -d  -p 11034:8086 \
-e INFLUXDB_DB=test \
-e INFLUXDB_ADMIN_USER=admin -e INFLUXDB_ADMIN_PASSWORD=admin \
-e INFLUXDB_USER=root -e INFLUXDB_USER_PASSWORD=root \
-v /disk1/dockerContainer/influxdb/conf:/etc/influxdb \
-v /disk1/dockerContainer/influxdb/databases:/var/lib/influxdb \
influxdb:1.8.2


```

>setfacl -m u:username:rwx -R /disk1/dockerContainer/influxdb

因此 手动 在/disk1/dockerContainer/influxdb/databases 下 创建

mv data  meta  wal

在 /disk1/dockerContainer/influxdb/conf下 创建配置文件
vi influxdb.conf

```
[meta]
  dir = "/var/lib/influxdb/meta"

[data]
  dir = "/var/lib/influxdb/data"
  engine = "tsm1"
  wal-dir = "/var/lib/influxdb/wal"

  series-id-set-cache-size = 100

[http]
  enabled = true
  bind-address = ":8086"
  auth-enabled = false

```

重启docker

>docker restart xxx

连上 influxdb  创建用户 

>create user "root" with password 'root' with ALL PRIVILEGES

然后把配置文件 auth-enabled = true 


重启docker

>docker restart xxx
