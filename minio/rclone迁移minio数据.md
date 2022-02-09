# rclone 迁移minio数据


两台机器的时区及时间要保持一致，最后进行迁移之前，两台机器的时间进行校准。

```
centos 7设置时区
timedatectl set-timezone Asia/Shanghai
#时间同步
ntpdate ntp.aliyun.com
```

>curl https://rclone.org/install.sh | sudo bash

rclone config
按照提示一步步，输入相应配置，即可。生成的文件位于：/root/.config/rclone目录下。
也可以直接 编辑如下文件

> vi /root/.config/rclone/rclone.conf

```
[oldminio]
type = s3
provider = Minio
env_auth = false
access_key_id = minio1
secret_access_key = minio1@123
region = cn-east-1
endpoint = https://minio.exemple.com
location_constraint =
server_side_encryption =

[newminio]
type = s3
provider = Minio
env_auth = false
access_key_id = minio2
secret_access_key = minio2@123
region = cn-east-1
endpoint = http://10.103.209.130:9000
location_constraint =
server_side_encryption =

```

>rclone sync -P oldminio:mybucket newminio:mybucket


    使用 sync 参数，同步有差异的数据    
    -P 显示详细过程
    mybucket 桶名称，新minio没有则自动创建
