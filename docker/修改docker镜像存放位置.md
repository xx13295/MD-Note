# 修改 docker容器位置

注意如果已经有容器 和镜像了 先统统删除 做好数据备份

vi /etc/docker/daemon.json

{
"registry-mirrors": ["https://2btbrkw6.mirror.aliyuncs.com"],
"storage-driver": "overlay2",
"graph":"/disk1/dockerRes/docker",
"storage-opts": ["overlay2.override_kernel_check=true"]
}

其中  /disk1/dockerRes/docker 替换为你需要更改的地址
 

重启生效

>systemctl restart docker

