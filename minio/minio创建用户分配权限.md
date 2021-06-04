# 分配新用户并赋予权限

    查看目前 local 存在的策略    

>mc admin policy list local

    查看readwrite格式

>mc admin policy info local readwrite

   可以仿照readwrite 创建一个新策略

> vi minio-test.json

    注意 json中不能有注释 复制请删除 

```aidl

{
  "Version": "2012-10-17",         
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [                      //  Action 表示（权限）
		"s3:ListAllMyBuckets",          //  查看所有的“桶”列表
		"s3:ListBucket",               //  查看桶内的对象列表
		"s3:GetBucketLocation",         
		"s3:GetObject",               //   下载对象
		"s3:PutObject",               //   上传对象
		"s3:DeleteObject"             //   删除对象
      ],
      "Resource": [
        "arn:aws:s3:::*"              // （应用到的资源， arn:aws:s3是命名空间 *表示所有）
                                      // arn:aws:s3:::test/* 这样表示仅给予Bucket为test的权限
      ]
    }
  ]
}


```
    压缩版

```aidl

{"Version":"2012-10-17","Statement":[{"Effect":"Allow","Action":["s3:ListAllMyBuckets","s3:ListBucket","s3:GetBucketLocation","s3:GetObject","s3:PutObject","s3:DeleteObject"],"Resource":["arn:aws:s3:::test/*"]}]}

```

    将这个新建的test策略放进local策略

>mc admin policy add local test minio-test.json


    创建新用户 test 密码为test1234

>mc admin user add local test test1234

    为该test用户赋予test策略

> mc admin policy set local test user=test

    禁用test用户

> mc admin user disable local test

    删除test用户

>mc admin user remove local test

    删除策略
    
>mc admin policy remove local test
