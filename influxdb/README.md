# influxdb 入门

	时序数据库（TSDB）特点
	
	1.持续高并发写入、无更新
	
	2.数据压缩存储
	
	3.低查询延时。
	
	常见 TSDB：influxdb、opentsdb、timeScaladb、Druid 等

### influxDB 结构

|字段|说明|
|:-|:-|
|database| 数据库 |
|measurement|数据库中的表|
|points|表里面的一行数据|


#### Point 数据的构成

	Point 由时间戳（time）、数据（field）、标签（tags）组成。
	
|Point属性 |说明|
|:-|:-|
|time    |记录时间是数据库中的主索引(会自动生成)|
|fields  |没有索引的属性|
|tags    |有索引的属性|


### windows 安装
 
 	
 	目前 已经存在2.0版本 但是windows版本暂时就 1.8.2

下载地址：
 
>https://dl.influxdata.com/influxdb/releases/influxdb-1.8.2_windows_amd64.zip
	
	打开 influxdb-1.8.2文件夹 创建 wal、data、meta 三个文件夹
	
	修改 `influxdb.conf`
	
	分别找到 [meta]  [data] 
	
	将底下的 dir 地址改为 相应的目录 
	
### 启动
	
> influxd.exe

### 客户端连接

> influx.exe

毕竟现在没有 开启 鉴权 所以无需 用户名密码	

### InfluxDB数据库常用命令
  
	1、显示所有数据库

>show databases

	2、 创建数据库

>create database ojbk

	3、 使用某个数据库

>use ojbk

	4、 显示所有表

>show measurements
		
	5、删除表

>drop measurement "tablename" 
	
	6、显示用户

>show users

	6、创建用户

>create user "test" with password 'test'
	
	7、创建管理员权限的用户

>create user "root" with password 'root' with ALL PRIVILEGES


	创建用户后 只需要在 influxdb.conf 中 [http] 标签下
	
	开启 鉴权 
	
	enabled = true
		
	下次 在用 客户端连接：
	
> influx.exe -username root -password root


	8、删除用户

>drop user "username"

	9、插入数据
	
	注意逗号 
	
[insert 表,tag1=value1,tag2=value2 field=value1]

>insert cpu,host=serverA,region=us_west value=0.64 
	
	10、查询数据

>select * from cpu

	11、删除数据

删除数据 只能 根据 tag 或者time 删除

>delete from cpu where time=1599190173708096700


### 连续查询（Continuous Queries）

当数据超过保存策略里指定的时间之后，就会被删除。

如果我们不想完全删除掉，比如做一个数据统计采样：

把原先每秒的数据，存为每小时的数据，让数据占用的空间大大减少（以降低精度为代价）。

这就需要InfluxDB提供的：

**连续查询（Continuous Queries）**

	 查看当前的查询策略

>SHOW CONTINUOUS QUERIES
	
	创建新的Continuous Queries

>CREATE CONTINUOUS QUERY cq_30m ON testDB BEGIN SELECT mean(temperature) INTO weather30m FROM weather GROUP BY time(30m) END

注释如下：

|name |说明|
|:-|:-|
|cq_30m|连续查询的名字|
|testDB|具体的数据库名|
|mean(temperature)|算平均温度|
|weather|当前表名|
|weather30m| 存新数据的表名|
|30m|时间间隔为30分钟|

当我们插入新数据之后，通过SHOW MEASUREMENTS查询发现。

可以发现数据库中多了一张名为weather30m(里面已经存着计算好的数据了)。
这一切都是通过Continuous Queries自动完成的。

	删除Continuous Queries

>DROP CONTINUOUS QUERY <cq_name> ON <database_name>

