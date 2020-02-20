# kafka 
	
	kafka解压
	
>tar -xzf kafka_2.12-2.1.0.tgz
	
	.
	
>cd kafka_2.12-2.1.0

	.

>vi config/server.properties 

	增加配置

	delete.topic.enable=true

	将listeners=PLAINTEXT://:9092   改为  listeners=PLAINTEXT://ip:9092

	

	运行kafka需要使用Zookeeper，所以需要先启动Zookeeper

>cd zookeeper-3.4.13/
	
	.
	
>bin/zkServer.sh start
	
	
### 启动kafka

>bin/kafka-server-start.sh config/server.properties &


### 创建一个主题(topic)	

>bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

	创建好之后，可以通过运行以下命令，查看已创建的topic信息：

>bin/kafka-topics.sh --list --zookeeper localhost:2181

### 发送消息 & 接收消息

>bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

>bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning


## 设置多个broker集群

1.首先为每个broker创建一个配置文件:

> cp config/server.properties config/server-1.properties 
> cp config/server.properties config/server-2.properties

>vi config/server-1.properties
 
	broker.id=1
	delete.topic.enable=true
	listeners=PLAINTEXT://ip:9093 
	log.dir=/tmp/kafka-logs-1
	
>vi config/server-2.properties 

	broker.id=2
	delete.topic.enable=true
	listeners=PLAINTEXT://ip:9094
	log.dir=/tmp/kafka-logs-2

	broker.id是集群中每个节点的唯一且永久的名称不可重复。

2.启动服务

> bin/kafka-server-start.sh config/server-1.properties &
 
> bin/kafka-server-start.sh config/server-2.properties &

3.创建一个新topic[ojbk]，把备份设置为：3

>bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 --partitions 1 --topic ojbk

3.监控

>bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic ojbk
	
	输出：

	Topic:ojbk      PartitionCount:1        ReplicationFactor:3     Configs:
	Topic: ojbk     Partition: 0    Leader: 0       Replicas: 0,1,2 Isr: 0,1,2
	
	
	"leader"：该节点负责该分区的所有的读和写，每个节点的leader都是随机选择的。
	"replicas"：备份的节点列表，无论该节点是否是leader或者目前是否还活着，只是显示。
	"isr"：“同步备份”的节点列表，也就是活着的节点并且正在同步leader。

	