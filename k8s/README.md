# kubernetes 

    官网 https://kubernetes.io/zh-cn/docs/home/


### 基于minikube 学习

在这之前必须要有docker的环境这很重要。

minikube能够快速搞定k8s的环境

但是装这玩意也是头疼的一批

由于网络问题，会导致start不起来，网上的一些过时的安装文章会让你带上阿里云的docker的仓库

但是那个仓库已经下载不到对应的镜像了，然后血压就上来了。最后使用代理的方式安装。

e.g
>export http_proxy="http://localhost:port"

>export https_proxy="http://localhost:port"

    
    官网  https://minikube.sigs.k8s.io/docs/start/


>curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64

>sudo install minikube-linux-amd64 /usr/local/bin/minikube && rm minikube-linux-amd64

>minikube start

可视化监控仪表面板

> minikube dashboard

当前窗口临时有效

>alias kubectl="minikube kubectl --"

编写demo.yaml文件
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ojbk
  namespace: demo
  labels:
    name: ojbk
spec:
  replicas: 3
  selector:
    matchLabels:
      app: ojbk
  template:
    metadata:
      name: ojbk
      namespace: demo
      labels:
        app: ojbk

    spec:
      # nodeSelector:
      # key: gitlab-dev
      containers:
        - name: ojbk
          image: registry.cn-hangzhou.aliyuncs.com/ojbk-plus/ojbk:1.0.1
          imagePullPolicy: IfNotPresent
          ports:
            - name: http
              containerPort: 8081
---
apiVersion: v1
kind: Service
metadata:
  name: ojbk-service
  namespace: demo
spec:
  selector:
    app: ojbk
  ports:
    - protocol: TCP
      port: 8081
      targetPort: 8081
      #nodePort: 31001
  type: NodePort

```

创建一个命名空间 demo

>kubectl create namespace demo

将demo.yaml应用到k8s里

>kubectl apply -f demo.yaml


获取pod  根据命名空间为demo 的

> kubectl get pod -n demo
 
端口转发才能被外头访问到
> kubectl port-forward ojbk-77f4b659b6-mnxjw  25666:8081 -n demo

或者使用
>minikube service ojbk-service -n demo