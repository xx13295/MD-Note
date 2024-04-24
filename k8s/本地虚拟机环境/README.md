# 首先搞三台虚拟机环境构建集群

    分别配置好网卡信息 
[具体可参考](../..//linux/虚拟机安装/README.md)

注意 以下内容 基本上都是 三台机器都要运行一次命令

## 1.基本环境

设置每个机器自己的hostname

>hostnamectl set-hostname xxx

将 SELinux 设置为 permissive 模式（相当于将其禁用）

>sudo setenforce 0

>sudo sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config

关闭swap
>sudo swapoff -a  

>sudo sed -ri 's/.*swap.*/#&/' /etc/fstab

允许 iptables 检查桥接流量
```
cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf
br_netfilter
EOF

```

```
cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF

```

>sudo sysctl --system

## 2.三件套kubelet、kubeadm、kubectl

#配置k8s的yum源地址
```
cat <<EOF | sudo tee /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=http://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=http://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg
http://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF

```
查看仓库有哪些版本

yum --showduplicates list kubelet

随便选个 1.27.6-0  吧 

安装三件套

>sudo yum install -y kubelet-1.27.6 kubeadm-1.27.6 kubectl-1.27.6

启动kubelet

>sudo systemctl enable --now kubelet


echo "192.168.0.131  k8s-master" >> /etc/hosts


初始化master节点

首先把防火墙关了 不然就打开 [6443 10250]

systemctl stop firewalld

systemctl disable firewalld



```
sudo kubeadm init \
--apiserver-advertise-address=192.168.0.131 \
--control-plane-endpoint=k8s-master \
--image-repository registry.aliyuncs.com/google_containers \
--kubernetes-version v1.27.6 \
--service-cidr=10.96.0.0/16 \
--pod-network-cidr=10.244.0.0/16



云服务器上安装最好用pod-network-cidr=192.168.0.0/16这样就无须多余的操作
上面由于是虚拟机所以ip要改一下导致后面的calico.yaml 也需要改ip为10.244.0.0/16
```


```
ps.如果出现
[init] Using Kubernetes version: v1.27.6
[preflight] Running pre-flight checks
error execution phase preflight: [preflight] Some fatal errors occurred:
        [ERROR CRI]: container runtime is not running: output: time="2024-04-19T13:03:14+08:00" level=fatal msg="validate service connection: CRI v1 runtime API is not implemented for endpoint \"unix:///var/run/containerd/containerd.sock\": rpc error: code = Unimplemented desc = unknown service runtime.v1.RuntimeService"
, error: exit status 1
[preflight] If you know what you are doing, you can make a check non-fatal with `--ignore-preflight-errors=...`
To see the stack trace of this error execute with --v=5 or higher

rm /etc/containerd/config.toml

containerd config default > /etc/containerd/config.toml

在默认的配置文件里找到sandbox_image。 设置为sandbox_image = "registry.aliyuncs.com/google_containers/pause:3.9"

systemctl restart containerd

```
sudo kubeadm reset
在重新init一次就会看到如下

```

Your Kubernetes control-plane has initialized successfully!

To start using your cluster, you need to run the following as a regular user:

  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config

Alternatively, if you are the root user, you can run:

  export KUBECONFIG=/etc/kubernetes/admin.conf

You should now deploy a pod network to the cluster.
Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
  https://kubernetes.io/docs/concepts/cluster-administration/addons/

You can now join any number of control-plane nodes by copying certificate authorities
and service account keys on each node and then running the following as root:

  kubeadm join k8s-master:6443 --token emhbuv.bwm6bukn2fsswvvm \
        --discovery-token-ca-cert-hash sha256:5a693641c5560b4f91591e4fa032ba8109190577dfed42df7e470e0bfd9ef46f \
        --control-plane 

Then you can join any number of worker nodes by running the following on each as root:

kubeadm join k8s-master:6443 --token emhbuv.bwm6bukn2fsswvvm \
        --discovery-token-ca-cert-hash sha256:5a693641c5560b4f91591e4fa032ba8109190577dfed42df7e470e0bfd9ef46f 
```

