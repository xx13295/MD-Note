docker pull eclipse-mosquitto
 
# 创建配置文件夹、日志文件夹
mkdir /disk1/dockerContainer/mosquitto/config
 
mkdir /disk1/dockerContainer/mosquitto/data
 
mkdir /disk1/dockerContainer/mosquitto/log



配置文件mosquitto.conf
```
#port 9001
listener 1883
#protocol websockets
persistence true
persistence_location /mosquitto/data
log_dest file /mosquitto/log/mosquitto.log
#关闭匿名模式
allow_anonymous false  
#指定密码文件
password_file /mosquitto/config/pwfile.conf 
per_listener_settings false
```

启动

```
docker run -itd --name=mosquitto --privileged \
-p 31183:1883 -p 39001:9001 \
-v /disk1/dockerContainer/mosquitto/config/mosquitto.conf:/mosquitto/config/mosquitto.conf \
-v /disk1/dockerContainer/mosquitto/config/pwfile.conf:/mosquitto/config/pwfile.conf \
-v /disk1/dockerContainer/mosquitto/data:/mosquitto/data \
-v /disk1/dockerContainer/mosquitto/log:/mosquitto/log \
-d  eclipse-mosquitto 
```
进入容器

>docker exec -it mosquitto sh

设置账户

>mosquitto_passwd -b /mosquitto/config/pwfile.conf [user] [passwd]

出容器后重启

>docker restart mosquitto