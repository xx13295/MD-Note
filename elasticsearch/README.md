# elasticsearch 

    1.官网
		https://www.elastic.co

	2.Github
		https://github.com/elastic/elasticsearch


# 安装  

	首先你得有java-jdk，否则你是无法正常使用es的

>https://www.elastic.co/cn/downloads/elasticsearch

	根据实际需求下载相应版本，本例子 使用elasticsearch-7.3.2-linux-x86_64.tar

	解压安装就不在多说!

	将解压后的 elasticsearch-7.3.2 放置于/usr/local/elasticsearch/ 目录下
	
1.创建用户 , elasticsearch不可使用root用户执行

>useradd -r elasticsearch

2.授权
> cd /usr/local/

>chown elasticsearch:elasticsearch -R elasticsearch

3.启动 

>cd /usr/local/elasticsearch/ elasticsearch-7.3.2/bin

>su elasticsearch

>./elasticsearch

	虽然会提示
		future versions of Elasticsearch will require Java 11; 
		your Java version from [/usr/local/java/jdk1.8.0_131/jre] 
		does not meet this requirement
	但不用慌张，软件是向下兼容的 我们使用jdk8一样可以正常启动
	
4.检验是否成功启动
	
>curl http://127.0.0.1:9200/

	出现以下内容证明 es 已启动 

	{
		"name": "localhost.localdomain",
		"cluster_name": "elasticsearch",
		"cluster_uuid": "0QKWfCxdTUislDnmmebV-w",
		"version": {
		"number": "7.3.2",
		"build_flavor": "default",
		"build_type": "tar",
		"build_hash": "1c1faf1",
		"build_date": "2019-09-06T14:40:30.409026Z",
		"build_snapshot": false,
		"lucene_version": "8.1.0",
		"minimum_wire_compatibility_version": "6.8.0",
		"minimum_index_compatibility_version": "6.0.0-beta1"
		},
		"tagline": "You Know, for Search"
	}
	

	但是 这个只能按照的机器本地访问， 如果想其他机器也访问这个 9200端口地址
	则需要修改 配置文件
	
5.修改配置文件
	
>vi /usr/local/elasticsearch/elasticsearch-7.3.2/config/elasticsearch.yml 

	找到 'network.host: 你本地ip'
	
	修改为 network.host: 0.0.0.0
	
	然后保存重启
	
6. 修改 network.host 无法正常启动问题

	根据错误提示 具体问题具体分析
	可能会出现以下问题
	
[1]: max file descriptors [4096] for elasticsearch process is too low, increase to at least [65535]
[2]: max number of threads [1024] for user [elasticsearch] is too low, increase to at least [4096]
[3]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
[4]: system call filters failed to install; check the logs and fix your configuration or 
disable system call filters at your own risk
[5]: the default discovery settings are unsuitable for production use; at least one of 
[discovery.seed_hosts, discovery.seed_providers, cluster.initial_master_nodes] must be configured
	
	大致上 如下 增加配置，若还出现问题 百度一下
	切换root用户 操作以下过程
	
>vi /etc/security/limits.d/90-nproc.conf
	将原来的1024 改为 4096 保存
>*          soft    nproc     4096

>vi /etc/security/limits.conf
	 在底下增加 4行 

>*               soft    nofile          65536
>*               hard    nofile          65536
>*               soft    nproc           4096
>*               hard    nproc           4096

>vi /etc/sysctl.conf
	增加 vm.max_map_count配置 
vm.max_map_count=262144

最后 切换回 es用户 修改 elasticsearch.yml

	增加 
	
	bootstrap.memory_lock: false
	bootstrap.system_call_filter: false
	cluster.initial_master_nodes: ["node-1"] 
 
 保存完事







	
	
	
	
	


	
	