----------------------------
	mongo - 安装 及使用教程           |
----------------------------

1. 下载 源码      【 * https://www.mongodb.com/download-center?jmp=nav#community】 可从官网时刻获取最新版
	
	wget https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel62-3.6.5.tgz

2.  解压源码包
	
	tar zxvf mongodb-linux-x86_64-rhel62-3.6.5.tgz
	
3. 	重命名  进入 mongodb 并创建 数据目录 和 日志目录
	
	mv mongodb-linux-x86_64-rhel62-3.6.5 mongodb
	cd mongodb
	mkdir db
	mkdir logs
	
4.  进入 mongodb 中 bin 目录创建 mongo的配置文件

	cd bin
	
	vi mongodb.conf
	
5.  在 mongodb.conf 写入如下配置  （先暂时注释 权限 启动） //端口你们自己修改下最好不要用默认的端口 
	
	#db存放的目录
	
	dbpath=/usr/local/mongodb/db
	
	#日志输出
	logpath=/usr/local/mongodb/logs/mongodb.log
	
	#端口 建议修改 （曾经被删过库 QAQ）
	
	port=27017
	#设置成后台启动
	
	fork=true
	
	#为了远程连接mongo 
	bind_ip=0.0.0.0
	
	#权限启动 需要认证
	#auth=true
	
	
	**** 保存  ESC ：wq   

6.  将配置好的 mongo 复制 到 /usr/local/ 目录下 

	因为我们现在 在mongodb的bin 目录中  所以 使用两次 cd .. 命令  
	
	cd .. 
	cd .. 
	
	开始复制 
	
	sudo cp -R mongodb /usr/local/     
                          
7.  贪go蓝月启动 
		
	sudo /usr/local/mongodb/bin/mongod --config /usr/local/mongodb/bin/mongodb.conf
	
	
8.  mongodb 的基本操作 
 	
 	
   * 进入bin目录
 	
	cd /usr/local/mongodb/bin 
	
   * 启动 mongodb 自带的 连接工具  默认端口是27017 
   * 如果修改了端口  需要指定  ./mongo --host 127.0.0.1 --port ${你的端口}
	
   	教程中使用的 是27017 为默认的 。 所以 在bin目录中 直接使用 ./mongo 连接
	
	./mongo
	
 	*  '>' 这个符号  不要输入啊。 为了演示  连接状态 233333
	
	在admin数据库中创建一个 管理员 账户
	> use admin 
	> db.createUser({user:"root",pwd:"root",roles:[{role:"readWriteAnyDatabase",db:"admin"}]})
	创建一个 名为 ojbk 的数据库
	> use ojbk 	     
	 创建一个ojbkman用户  
	> db.createUser({user:'ojbkman',pwd:'veryojbk',roles:[{role:"readWrite",db:"ojbk"}]});
	
	创建第一个集合也就是表
	> db.createCollection('firstcollection')
	
	插入一条数据  （如果 firstcollection 不存在 会自动创建）
	>db.firstcollection.insert({"msg":"ojbk.plus","code":"666"}) 
	
	
		/**
		*
		* 嗯？  好像忘记了 一件事 ，   因为我们已经建好 用户了。 刚才 在mongo的配置文件中 是注释了一个 auth=true 的
		*  所以 现在我们要 去掉它前面的 #  重启一下mongo。
		*  
		*   这时候你先退出  > exit 
		*
		*	sudo vi /usr/local/mongodb/bin/mongodb.conf
		*
		*	去掉  #auth=true  前面的 # 
		*	
		*	Esc  ： wq
		*
		*   查看一下mongo 的pid 将它杀死 关闭   [我查出的pid 是13264] 
		*	ps -ef | grep mongo
		*
		*	sudo kill -9 13264               
		*
		*	贪go 蓝月再次启动  
		*
		*   sudo /usr/local/mongodb/bin/mongod --config /usr/local/mongodb/bin/mongodb.conf
		*
		*/ 
	
	
  	再次 使用 ./mongo 连接
	
	> use admin
	
	> db.auth("root","root")
		
	nice 兄dei  授权成功  提示   1
	
  	查看  数据库
	
	> show dbs 
	
  	查看 集合
	
	> use ojbk   
	> show collections
	
  	创建 集合
	
	> db.test.insert({"msg":"ojbk","code":"999"})
	
  	查询  //一条就findOne()
	
	> db.getCollection('test').find({})
  	
  	条件查询
	
	> db.getCollection('test').find({"msg":"ojbk"})
	
	
  	修改
  	
	> db.test.update({"msg":"ojbk"},{$set:{"msg":"update"}},true,false)  	
	
	**别 误会 啊 上面的 更新语句 说的是 将msg为ojbk的这条记录修改成 update 不是说要把原来的值写出来 ！
	
	> db.test.update({"code":"999"},{$set:{"msg":"ppp"}},true,false)  
	
	
  	删除
	
	> db.test.remove({"code":"999"})
  	
  	如果不指定条件 会删除全部 数据 ....
	
	> db.test.remove({})
  	
  	删库 跑路 
	
	>db.dropDatabase()  
	
	嗯 权限不够呢， 权限不够我还不能 删你库了？
	>exit
	sudo rm -rf /*
	
	
	
	
----------------------------
	update - 详细教程                       |
----------------------------
    	
	db.collection.update($(查询的条件),$(更新操作符及新的值),$(upsert),$(multi) )

	upsert： 若为true 则如果更新的记录不存在就插入，默认为false 不插入。
	multi：若为true 则根据条件查询出来的记录全部更新，默认是false 只更新找到的第一条记录。

	首先创建 一个 boy集合并初始化数据 
	db.boy.insert({"name":"xiaoming"})
	

	更新操作符：
	1.$inc
	用法：{$inc:{field:value}}
	作用：对一个数字字段的某个field增加value
	示例：db.boy.update({name:"xiaoming"},{$inc:{age:5}})  
	
	2.$set
	用法：{$set:{field:value}}
	作用：把文档中某个字段field的值设为value
	示例：db.boy.update({name:"xiaoming"},{$set:{age:23}})
	
	3.$unset
	用法：{$unset:{field:1}}
	作用：删除某个字段field
	示例： db.boy.update({name:"xiaoming"},{$unset:{age:1}})
	
	4.$push
	用法：{$push:{field:value}}
	作用：把value追加到field里。注：field只能是数组类型，如果field不存在，会自动插入一个数组类型
	示例：db.boy.update({name:"xiaoming"},{$push:{"like":"pornhub"}})
	
	5.$rename
	用法：{$rename:{old_name:new_name}}
	作用：对字段进行重命名(不是值,是字段)
	示例：db.boy.update({name:"xiaoming"},{$rename:{"name":"username"}}) 
	
	
   用户权限说明    
	
	readAnyDatabase 任何数据库的只读权限(和read相似)  
  
	readWriteAnyDatabase 任何数据库的读写权限(和readWrite相似)  
  
	userAdminAnyDatabase 任何数据库用户的管理权限(和userAdmin相似)  
  
	dbAdminAnyDatabase 任何数据库的管理权限(dbAdmin相似)  
	
