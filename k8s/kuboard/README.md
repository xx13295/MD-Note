# Kuboard

    官网 https://www.kuboard.cn/


### 这玩意真的强大k8s可视化界面降低学习成本。
   学习 https://www.kuboard.cn/learning/

   安装 https://www.kuboard.cn/install/v3/install.html



在安装之前如果你是和我一样用wsl子系统安装的minikube来安装学习，
直接用在线安装这很可能会导致运行不起来。

查看了 常见错误 中关于 缺少 Master Role 的描述 。不管三七二十一 就是干

kubectl get nodes 得到的肯定就是minikube 所以执行以下命令再去
>kubectl label nodes minikube k8s.kuboard.cn/role=etcd

然后再去在线安装经过漫长的等待即可。

>kubectl apply -f https://addons.kuboard.cn/kuboard/kuboard-v3.yaml

然后使用如下命令 即可打开 控制面板

>minikube service kuboard-v3 -n kuboard