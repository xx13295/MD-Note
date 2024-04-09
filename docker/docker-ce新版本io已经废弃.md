# 新版docker 安装

>yum-config-manager --add-repo   http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

> sudo yum install docker-ce

### ubuntu

>sudo apt install -y curl gnupg2 software-properties-common apt-transport-https ca-certificates

>curl -fsSL http://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo apt-key add -

 添加仓库

>sudo add-apt-repository "deb [arch=amd64] http://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable"

>sudo apt update

安装docker

>sudo apt-get -y install docker-ce