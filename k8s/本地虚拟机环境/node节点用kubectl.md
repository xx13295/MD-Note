# 解决 K8S 命令 kubectl 无法在Node节点上使用


```
[root@k8s-node2 ~]# kubectl get pods -A
E0427 12:11:03.541298    6587 memcache.go:265] couldn't get current server API group list: Get "http://localhost:8080/api?timeout=32s": dial tcp [::1]:8080: connect: connection refused
E0427 12:11:03.542243    6587 memcache.go:265] couldn't get current server API group list: Get "http://localhost:8080/api?timeout=32s": dial tcp [::1]:8080: connect: connection refused
E0427 12:11:03.543200    6587 memcache.go:265] couldn't get current server API group list: Get "http://localhost:8080/api?timeout=32s": dial tcp [::1]:8080: connect: connection refused
E0427 12:11:03.545143    6587 memcache.go:265] couldn't get current server API group list: Get "http://localhost:8080/api?timeout=32s": dial tcp [::1]:8080: connect: connection refused
E0427 12:11:03.547269    6587 memcache.go:265] couldn't get current server API group list: Get "http://localhost:8080/api?timeout=32s": dial tcp [::1]:8080: connect: connection refused
The connection to the server localhost:8080 was refused - did you specify the right host or port?

```


    将主节点中的 /etc/kubernetes/admin.conf 文件拷贝到 node节点相同目录下，然后对从节点配置环境变量


我们在master节点上 执行以下scp命令再输入登录密码

>scp /etc/kubernetes/admin.conf root@192.168.0.133:/etc/kubernetes/


然后进入对应的节点配置环境变量

>echo "export KUBECONFIG=/etc/kubernetes/admin.conf" >> ~/.bash_profile
 
>source ~/.bash_profile


