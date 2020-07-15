# 1.依赖 
	
	  <dependency>
            <groupId>io.minio</groupId>
            <artifactId>minio</artifactId>
            <version>7.1.0</version>
        </dependency>

        
# 2.示例 


```
	
	import io.minio.*;
	import io.minio.http.Method;
	
	import java.io.ByteArrayInputStream;
	import java.util.concurrent.TimeUnit;
	
	public class MinioTest {

	    public static void main(String[] args) throws Exception {
	
	        MinioClient minioClient = MinioClient.builder()
	                .endpoint("http://106.52.14.247:33333")
	                .credentials("xhajshfjgha", "sdhjahwxcsd")
	                .build();
	
	        //检查 bucket 是否存在 不存在就创建
	        boolean isExist =  minioClient.bucketExists(BucketExistsArgs.builder().bucket("ojbk").build());
	        if(isExist) {
	            System.out.println("Bucket already exists.");
	        } else {
	            minioClient.makeBucket(MakeBucketArgs.builder().bucket("ojbk").build());
	        }
	        //文件上传  选择 ojbk 这个 bucket  将/home/wxm/webflux/webflux.log 这个文件 存储在minio服务器上 命名为ojbk.log
	        minioClient.uploadObject(UploadObjectArgs.builder().bucket("ojbk").object("ojbk.log").filename("/home/wxm/webflux/webflux.log").build());
	        //minioClient.putObject(PutObjectArgs.builder().bucket("ojbk").object("ojbk.log").stream(inputStream, objectSize, partSize).build());
	        //bucket 创建 plus 空文件夹
	        //minioClient.putObject(PutObjectArgs.builder().bucket("ojbk").object("plus/").stream(new ByteArrayInputStream(new byte[] {}), 0, -1).build());
	        // 获取我们 刚刚上传的文件的url
	        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).expiry(1, TimeUnit.HOURS).bucket("ojbk").object("ojbk.log").build());
	
	        System.err.println(url);
	        //下载 minio 中的文件  ojbect 对应服务器中的文件名字 ，filename 对应 下载存储的路径和文件名
	        minioClient.downloadObject(DownloadObjectArgs.builder().bucket("ojbk").object("ojbk.log").filename("/home/wxm/webflux/666.log").build());
	
	        //删除文件
	        //minioClient.removeObject(RemoveObjectArgs.builder().bucket("ojbk").object("ojbk.log").build());
	
	    }

	}
	
```

# 更多详情 


	https://docs.min.io/docs/java-client-api-reference
	
	
	http://minio.github.io/minio-java/io/minio/MinioClient.html
	
	
