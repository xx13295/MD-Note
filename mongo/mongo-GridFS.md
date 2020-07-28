# GridFS简介
	
	GridFS是用于存储和检索超过16MB的BSON文档大小限制的文件的解决方案。

	GridFS是MongoDB用来存储大型二进制文件的一种存储机制。

	GridFS 不是将文件存储在单个文档中，而是将文件分为块，并将每个块作为单独的文档存储。
	一般情况GridFS使用的块大小为256kb,最后一个块除外。

	GridFS 使用两个集合存储文件，一个集合文件的块，另一个存储文件的原始数据。

# GridFS 优缺点

### 优点

	能够简化技术栈,如果已经使用了MongoDB,那么使用GridFS,就不需要其它独立的存储工具了

	GridFS会自动平衡已有的复制,或者为MongoDB设置的自动分片,所以对文件存储做故障转移或者是横向扩展会更容易 。

	GridFS的功能不错,能自动解决一些其他文件系统遇到的问题,如在同一个目录下存储大量的文件

### 缺点
	
	性能较低，不如直接访问文件系统快。

	无法修改文档。如果要修改GridFS里面的文档，只能是先删除再添加

##  要点 

	MongoDB 不会释放已经占用的硬盘空间。即使删除 db中的集合也不会释放磁盘空间。
	因此 如果使用 GridFS 存储文件，从 GridFS 存储中删除无用的垃圾文件， 
	MongoDB 依然不会释放磁盘空间的。这会造成磁盘一直在消耗，而无法回收利用的问题。

	因此 这 几乎是这个 最大的弊端  所以很少人会用mongo 来存文件 
	
	
### 释放空间

	在mongo shell中运行 【二选一】
	
	db.repairDatabase()
	
	或者
	
	db.runCommand({ repairDatabase: 1 })
	
	使用通过修复数据库方法回收磁盘时需要注意，
	
	但修复磁盘的剩余空间必须大于等于存储数据集占用空间家伙加上 2G ，否则无法完成修复。

	
	
## 代码 

```

	import com.mongodb.MongoClient;
	import com.mongodb.client.MongoDatabase;
	import com.mongodb.client.gridfs.GridFSBucket;
	import com.mongodb.client.gridfs.GridFSBuckets;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	
	@Configuration
	public class MongoConfig {
	    @Value("${mongodb.database:ojbktest}")  //这里是数据库的名称
	    String db;
	
	    @Bean
	    public GridFSBucket getGridFSBucket(MongoClient mongoClient) {
	        MongoDatabase database = mongoClient.getDatabase(db);
	        GridFSBucket bucket = GridFSBuckets.create(database);
	        return bucket;
	    }
	}

```




```

	import com.mongodb.client.gridfs.GridFSBucket;
	import com.mongodb.client.gridfs.GridFSDownloadStream;
	import com.mongodb.client.gridfs.model.GridFSFile;
	import org.bson.types.ObjectId;
	import org.junit.jupiter.api.Test;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.data.mongodb.core.query.Criteria;
	import org.springframework.data.mongodb.core.query.Query;
	import org.springframework.data.mongodb.gridfs.GridFsResource;
	import org.springframework.data.mongodb.gridfs.GridFsTemplate;
	
	import java.io.*;
	
	@SpringBootTest
	public class MongoApplicationTests {
	
	    @Autowired
	    private GridFsTemplate gridFsTemplate;
	
	    @Test
	    public void uploadFile() throws FileNotFoundException {
	        //要存储的文件
	        File file = new File("D:\\ok.jpg");
	        //定义输入流
	        FileInputStream inputStram = new FileInputStream(file);
	        //向GridFS存储文件
	        ObjectId objectId = gridFsTemplate.store(inputStram, "ok6.jpg");
	        //得到文件ID
	        String fileId = objectId.toString();
	        System.out.println(fileId);
	    }
	
	
	    @Autowired
	    private GridFSBucket gridFSBucket;
	
	    @Test
	    public void downloadFile() throws IOException {
	        String fileId = "5f1fc01e6c97742656939265";
	        //根据id查询文件
	        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
	        //打开下载流对象
	        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
	        //创建gridFsResource，用于获取流对象
	        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
	        //获取流中的数据
	        InputStream inputStream = gridFsResource.getInputStream();
	        File f1 = new File("D:\\usr\\ok.jpg");
	        if (!f1.exists()) {
	            f1.getParentFile().mkdirs();
	        }
	        byte[] bytes = new byte[1024];
	        // 创建基于文件的输出流
	        FileOutputStream fos = new FileOutputStream(f1);
	        int len = 0;
	        while ((len = inputStream.read(bytes)) != -1) {
	            fos.write(bytes, 0, len);
	        }
	        inputStream.close();
	        fos.close();
	
	    }
	
	    @Test
	    public void testDelFile()  {
	
	        String fileId = "5f1fc01e6c97742656939265";
	        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));
	
	    }
	
	
	}

```

