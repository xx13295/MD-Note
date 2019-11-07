----------------------------
	mongo - 索引使用教程           |
----------------------------

### mongo索引的使用
	
	 我们知道常用的mysql等数据库有索引，那mongo是否也有索引呢?
	 
		答案是肯定的。	
		
###  建立索引

>db.collection.createIndex(key, options)


>db.getCollection('test_a').createIndex({"specs":NumberInt(1)},{"name":"test.specs","background":true})

	test_a   -->  为集合名称 ，也就是表。
	
	{"specs":NumberInt(1)}  --> specs 为需要建立索引的key名， vlaue 可以是 NumberInt(1) 或NumberInt(-1) ， 1表示升序，-1降序。
	
	{"name":"test.specs","background":true}  -->   test.specs 为当前索引的别名， background值为true 代表后台建立这个索引。
	
	
##### options 可选参数

| 参数		|类型		    | 描述							|
| --------  | --------  | --------						|
|name		|String		|索引名称，默认是：字段名_排序类型 开始排序	|
|background |boolean	|创建索引在后台运行，不会阻止其他对数据库操作	|
|unique		|boolean	|创建唯一索引，不会出现重复的文档值			|
|sparse		|boolean	|过滤掉null，不存在的字段				|
|expireAfterSeconds|Integer|指定一个以秒为单位的数值，完成 TTL设定，设定集合的生存时间|
|v			|Index version|索引的版本号。默认的索引版本取决于mongod创建索引时运行的版本|
|weights|document	|索引权重值，数值在 1 到 99,999 之间，表示该索引相对于其他索引字段的得分权重。|
|default_language	|String	|对于文本索引，该参数决定了停用词及词干和词器的规则的列表。 默认为英语|
|language_override	|String	|对于文本索引，该参数指定了包含在文档中的字段名，语言覆盖默认的language，默认值为 language.|

	
	
### 查询集合索引以及索引大小
	
>db.collection.getIndexes()

>db.getCollection('test_a').getIndexes()

	[
    {
        "v" : 1,
        "key" : {
            "_id" : 1
        },
        "name" : "_id_",
        "ns" : "ojbktest.test_a"
    },
    {
        "v" : 1,
        "key" : {
            "title" : 1.0
        },
        "name" : "test.title",
        "ns" : "ojbktest.test_a",
        "background" : true
    }
	]
	
	一个是主键id 索引，另一个是我们手动创建的title索引
	
	
		
 	
 	查看索引大小
 	
>db.getCollection('test_a').totalIndexSize()

### 删除索引

	删除指定的索引

>db.collection.dropIndex(indexName) 


>db.getCollection('test_a').dropIndex("test.title") 


	删除 除了_id_ 以外所有其他手动创建的索引。

>db.collection.dropIndexes() 

>db.getCollection('test_a').dropIndexes() 
	
### 查看查询语句是否使用索引

>db.collection.find().explain()

>db.getCollection('test_a').find({catalog:"运动"}).explain()


	{
    "queryPlanner" : {
        "plannerVersion" : 1,
        "namespace" : "ojbktest.test_a",
        "indexFilterSet" : false,
        "parsedQuery" : {
            "catalog" : {
                "$eq" : "运动"
            }
        },
        "winningPlan" : {
            "stage" : "FETCH",
            "inputStage" : {
                "stage" : "IXSCAN",
                "keyPattern" : {
                    "catalog" : 1
                },
                "indexName" : "test.catalog",
                "isMultiKey" : false,
                "isUnique" : false,
                "isSparse" : false,
                "isPartial" : false,
                "indexVersion" : 1,
                "direction" : "forward",
                "indexBounds" : {
                    "catalog" : [ 
                        "[\"运动\", \"运动\"]"
                    ]
                }
            }
        },
        "rejectedPlans" : []
    },
    "serverInfo" : {
        "host" : "ojbk",
        "port" : 50168,
        "version" : "3.2.4",
        "gitVersion" : "e2ee9ffcf9f5a94fad76802e28cc978718bb7a30"
    },
    "ok" : 1.0
	}


由上面的结果可知 是用到了索引 "test.catalog"

#### stage 对照表

| 参数				| 描述										|
| --------  		| --------									|
|COLLSCAN			| 全表扫描										|
|IXSCAN				|扫描索引										|
|FETCH				|根据索引去检索指定document						|
|SHARD_MERGE		|将各个分片返回数据进行merge						|
|SORT				|表明在内存中进行了排序								|
|LIMIT				|使用limit限制返回数								|
|SKIP				|使用skip进行跳过								|
|IDHACK				|针对_id进行查询								|
|SHARDING_FILTER	|通过mongos对分片数据进行查询						|
|COUNT				|利用db.coll.explain().count()之类进行count运算	|
|COUNTSCAN			|count不使用Index进行count时的stage返回			|
|COUNT_SCAN			|count使用了Index进行count时的stage返回			|
|SUBPLA				|未使用到索引的$or查询的stage返回					|
|TEXT				|使用全文索引进行查询时候的stage返回					|
|PROJECTION			|限定返回字段时候stage的返回						|


##### 期望查询使用到索引的stage结果

	Fetch+IDHACK
	
	Fetch+ixscan
	
	Limit+（Fetch+ixscan）
	
	PROJECTION+ixscan
	
	SHARDING_FITER+ixscan
	
	COUNT_SCAN

##### 不期望的结果 

	COLLSCAN(全表扫描),
	SORT(使用sort但是无index),
	不合理的SKIP,
	SUBPLA(未用到index的$or),
	COUNTSCAN(不使用index进行count)



#### explain() 还可以设置参数

	executionStats

	allPlansExecution
	
	
>db.getCollection('test_a').find({catalog:"运动"}).explain("executionStats")

	{
	    "queryPlanner" : {
	        "plannerVersion" : 1,
	        "namespace" : "ojbktest.test_a",
	        "indexFilterSet" : false,
	        "parsedQuery" : {
	            "catalog" : {
	                "$eq" : "运动"
	            }
	        },
	        "winningPlan" : {
	            "stage" : "FETCH",
	            "inputStage" : {
	                "stage" : "IXSCAN",
	                "keyPattern" : {
	                    "catalog" : 1
	                },
	                "indexName" : "test.catalog",
	                "isMultiKey" : false,
	                "isUnique" : false,
	                "isSparse" : false,
	                "isPartial" : false,
	                "indexVersion" : 1,
	                "direction" : "forward",
	                "indexBounds" : {
	                    "catalog" : [ 
	                        "[\"运动\", \"运动\"]"
	                    ]
	                }
	            }
	        },
	        "rejectedPlans" : []
	    },
	    "executionStats" : {
	        "executionSuccess" : true,
	        "nReturned" : 1,
	        "executionTimeMillis" : 0,
	        "totalKeysExamined" : 1,
	        "totalDocsExamined" : 1,
	        "executionStages" : {
	            "stage" : "FETCH",
	            "nReturned" : 1,
	            "executionTimeMillisEstimate" : 0,
	            "works" : 2,
	            "advanced" : 1,
	            "needTime" : 0,
	            "needYield" : 0,
	            "saveState" : 0,
	            "restoreState" : 0,
	            "isEOF" : 1,
	            "invalidates" : 0,
	            "docsExamined" : 1,
	            "alreadyHasObj" : 0,
	            "inputStage" : {
	                "stage" : "IXSCAN",
	                "nReturned" : 1,
	                "executionTimeMillisEstimate" : 0,
	                "works" : 2,
	                "advanced" : 1,
	                "needTime" : 0,
	                "needYield" : 0,
	                "saveState" : 0,
	                "restoreState" : 0,
	                "isEOF" : 1,
	                "invalidates" : 0,
	                "keyPattern" : {
	                    "catalog" : 1
	                },
	                "indexName" : "test.catalog",
	                "isMultiKey" : false,
	                "isUnique" : false,
	                "isSparse" : false,
	                "isPartial" : false,
	                "indexVersion" : 1,
	                "direction" : "forward",
	                "indexBounds" : {
	                    "catalog" : [ 
	                        "[\"运动\", \"运动\"]"
	                    ]
	                },
	                "keysExamined" : 1,
	                "dupsTested" : 0,
	                "dupsDropped" : 0,
	                "seenInvalidated" : 0
	            }
	        }
	    },
	    "serverInfo" : {
	        "host" : "ojbk",
	        "port" : 50168,
	        "version" : "3.2.4",
	        "gitVersion" : "e2ee9ffcf9f5a94fad76802e28cc978718bb7a30"
	    },
	    "ok" : 1.0
	}
