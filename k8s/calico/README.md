# calico

查看 calico向k8s中添加的api资源

>kubectl api-resources |grep calico


这些api资源是属于calico的，因此不建议使用kubectl来管理。

推荐用calicoctl来管理这些api资源，可以将calicoctl安装为kubectl的插件。
```

cd /usr/local/bin

curl -o kubectl-calico -O -L  "https://github.com/projectcalico/calicoctl/releases/download/v3.21.5/calicoctl-linux-amd64" 

chmod +x kubectl-calico

```

验证插件正常工作

>kubectl calico -h