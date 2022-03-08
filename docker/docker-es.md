#  elasticsearch docker 安装



```

docker run --privileged=true -d -e ES_JAVA_POTS="-Xms256m -Xmx512m" -e "discovery.type=single-node" -p 9200:9200 -p 9300:9300 --name es7.12.1 \
-v /disk1/dockerContainer/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v /home/esdata/data:/usr/share/elasticsearch/data \
-v /etc/localtime:/etc/localtime \
-v /disk1/dockerContainer/elasticsearch/plugins:/usr/share/elasticsearch/plugins elasticsearch:7.12.1



```


###

可以设置容器跟随Docker启动

>docker update es7.12.1 --restart=always
