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

```
cat > /etc/sysconfig/modules/ipvs.modules <<EOF
#!/bin/bash
modprobe -- ip_vs
modprobe -- ip_vs_rr
modprobe -- ip_vs_wrr
modprobe -- ip_vs_sh
modprobe -- nf_conntrack_ipv4
EOF
```
chmod 755 /etc/sysconfig/modules/ipvs.modules && bash /etc/sysconfig/modules/ipvs.modules && lsmod | grep -e ip_vs -e nf_conntrack_ipv4

yum install -y ipset ipvsadm


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

>sudo sysctl --system

## 2.三件套kubelet、kubeadm、kubectl

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

### 方式一
执行 初始化 如果失败可以使用 kubeadm reset 后重复执行init
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

然后node1 也需要 配置一下containerd 然后restart才能kubeadm join 否则会出现 [ERROR CRI]

calico安装

应用operator资源清单文件

kubectl create -f https://raw.githubusercontent.com/projectcalico/calico/v3.25.1/manifests/tigera-operator.yaml

wget https://raw.githubusercontent.com/projectcalico/calico/v3.25.1/manifests/custom-resources.yaml

将custom-resources.yaml中的
cidr: 192.168.0.0/16 改为实际的cidr 比如10.244.0.0/16

安装calico 出现的问题
```
kubectl describe pod coredns-64897985d-ghwtc -n kube-system

发现最后显示 network: stat /var/lib/calico/nodename: no such file or directory:

Warning FailedCreatePodSandBox 6m20s (x5981 over 17h) kubelet (combined from similar events): 
Failed to create pod sandbox: rpc error:
 code = Unknown desc = failed to set up sandbox container 
 "ecb5ac36e4c73944bfb0d180425232e88b378d9238ce8d40951721210178ec09"
  network for pod "coredns-64897985d-ghwtc": networkPlugin cni failed to set up pod
   "coredns-64897985d-ghwtc_kube-system" 
   network: stat /var/lib/calico/nodename: no such file or directory:
    check that the calico/node container is running and has mounted /var/lib/calico/
   那创建 nodename 文件 
   
mkidr /var/lib/calico/
touch /var/lib/calico/nodename

```


问题[failed to find plugin "flannel" in path [/opt/cni/bin]
```

进入指定目录查看，是否有flannel
cd /opt/cni/bin

解决：

如果缺少flannel，则需要下载CNI插件
https://github.com/containernetworking/plugins/releases

wget https://github.com/containernetworking/plugins/releases/download/v0.8.6/cni-plugins-linux-amd64-v0.8.6.tgz

mkdir -p /opt/cni-plugins/
tar -zxvf cni-plugins-linux-amd64-v0.8.6.tgz -C /opt/cni-plugins/
cp /opt/cni-plugins/flannel /opt/cni/bin/

```


验证 安装一个Dashborad 玩一下

kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.7.0/aio/deploy/recommended.yaml

kubectl edit svc kubernetes-dashboard -n kubernetes-dashboard

将 type: ClusterIP 改为 type: NodePort       
ClusterIP=集群内访问，NodePort=集群外也可以访问

kubectl get svc -A |grep kubernetes-dashboard

访问： https://集群任意IP:端口      https://192.168.0.133:32064

#创建访问账号，准备一个yaml文件； vi dash.yaml
```
apiVersion: v1
kind: ServiceAccount
metadata:
  name: admin-user
  namespace: kubernetes-dashboard
---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: admin-user
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
- kind: ServiceAccount
  name: admin-user
  namespace: kubernetes-dashboard
```
然后应用一下
kubectl apply -f dash.yaml

kubectl -n kubernetes-dashboard create token admin-user

就会获得token

直接用token登录完事


### 方式2


kubeadm.yaml
```
apiVersion: kubeadm.k8s.io/v1beta3
kind: InitConfiguration
localAPIEndpoint:
  advertiseAddress: 192.168.0.131
  bindPort: 6443
nodeRegistration:
  criSocket: unix:///run/containerd/containerd.sock
  taints:
  - effect: PreferNoSchedule
    key: node-role.kubernetes.io/master
---
apiVersion: kubeadm.k8s.io/v1beta3
kind: ClusterConfiguration
kubernetesVersion: 1.27.6
imageRepository: registry.aliyuncs.com/google_containers
networking:
  podSubnet: 10.244.0.0/16
---
apiVersion: kubelet.config.k8s.io/v1beta1
kind: KubeletConfiguration
cgroupDriver: systemd
failSwapOn: false
---
apiVersion: kubeproxy.config.k8s.io/v1alpha1
kind: KubeProxyConfiguration
mode: ipvs
```

>kubeadm config images pull --config kubeadm.yaml

>kubeadm init --config kubeadm.yaml



安装 helm

Helm是Kubernetes的包管理器，Helm可以安装Kubernetes的一些常用组件。

```
wget https://get.helm.sh/helm-v3.10.3-linux-amd64.tar.gz
tar -zxvf helm-v3.10.3-linux-amd64.tar.gz
mv linux-amd64/helm  /usr/local/bin/

或者直接脚本

curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash

网站https://helm.sh/zh/docs/intro/install/

```
