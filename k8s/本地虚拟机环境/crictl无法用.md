# crictl 无法使用问题


```
出现错误
WARN[0000] image connect using default endpoints: [unix:///var/run/dockershim.sock unix:///run/containerd/containerd.sock unix:///run/crio/crio.sock unix:///var/run/cri-dockerd.sock]. As the default settings are now deprecated, you should set the endpoint instead. 
E0429 22:53:57.729931   41409 remote_image.go:119] "ListImages with filter from image service failed" err="rpc error: code = Unavailable desc = connection error: desc = \"transport: Error while dialing dial unix /var/run/dockershim.sock: connect: no such file or directory\"" filter="&ImageFilter{Image:&ImageSpec{Image:,Annotations:map[string]string{},},}"
FATA[0000] listing images: rpc error: code = Unavailable desc = connection error: desc = "transport: Error while dialing dial unix /var/run/dockershim.sock: connect: no such file or directory" 
```

解决方案

```
cat > /etc/crictl.yaml <<EOF
runtime-endpoint: unix:///var/run/containerd/containerd.sock
image-endpoint: unix:///var/run/containerd/containerd.sock
timeout: 0
debug: false
pull-image-on-create: false
EOF
```

>systemctl daemon-reload



>crictl image


>ctr namespaces ls

>ctr -n k8s.io images pull docker.io/library/mysql:latest