###Let's Encrypt 宣布 ACME v2 正式支持通配符证书。Let's Encrypt 宣称将继续清除 Web 上采用 HTTPS 的障碍，让每个网站轻松获取管理证书。

####  acme.sh 
[传送门1](https://github.com/Neilpang/acme.sh/wiki/%E8%AF%B4%E6%98%8E/)

  
#### 参考 该博主
[传送门2](https://my.oschina.net/kimver/blog/1634575/)


### 执行以下命令 

<s>	curl https://get.acme.sh | sh </s>

my@example.com为你的邮箱

>curl https://get.acme.sh | sh -s email=my@example.com

刷新环境变量

>source ~/.bashrc


或者从github安装

>wget -O - https://raw.githubusercontent.com/acmesh-official/acme.sh/master/acme.sh | sh -s -- --install-online -m  my@example.com

#### 导入阿里云后台的密钥 [获取key](https://ak-console.aliyun.com/#/accesskey/)

	export Ali_Key="dsadhasdkjaskdjsds"
	export Ali_Secret="dsfhsfsfksfsldfsfds"

#### 填写自己的域名生成证书
	acme.sh --issue --dns dns_ali -d ojbk.plus -d *.ojbk.plus
	
#### 在证书生成目录执行
```

acme.sh --installcert -d ojbk.plus -d *.ojbk.plus  \
	--keypath       /usr/local/ssl/ojbk/ojbk.plus.key  \
	--fullchainpath /usr/local/ssl/ojbk/ojbk.plus.pem

```
	
如果有配置nginx脚本可以自动重载ssl

```
acme.sh --installcert -d ojbk.plus -d *.ojbk.plus  \
	--keypath       /usr/local/ssl/ojbk/ojbk.plus.key  \
	--fullchainpath /usr/local/ssl/ojbk/ojbk.plus.pem \
	--reloadcmd     "service nginx force-reload"
```


	



	//这样就会把key和pem生成到指定的目录   
	
	当然 首先 你先 进入 到 usr/local/    使用 mkdir 命令创建文件夹 
	创建 ssl 文件 夹、再 进入ssl文件夹中 创建ojbk 文件夹 
	注意 将 权限修改一下 
	修改文件的所有者和组 【组:用户】
	chown -R ojbk:ojbk ssl  
	
	或者使用 sudo setfacl -m u:ojbk:rwx -R ssl/  
	设定ojbk用户对ssl 文件夹拥有rwx权限



##  好了 ，这样就拥有了免费的ssl 证书 三个月一次 他会自动 帮你重新 更新证书的


## 2020 / 02 /10  证书无法更新问题

	需要 更新一下 脚本 不然一直卡在 Getting domain auth token for each domain
	
> /usr/local/acme.sh/acme.sh --upgrade

	然后重新执行 更新脚本的命令

# 2021 / 11 / 23 证书由原来的Let’s Encrypt变成了ZeroSSL

    由于证书过期了 本能的使用了上面的更新脚本命令 -upgrade
    更新后发现 acme 默认证书已经换成了 ZeroSSL

    根据提示直接 输入以下命令
    acme.sh  --register-account  -m myemail@example.com --server zerossl

    myemail@example.com 为你邮箱 随便填都行。

    然后重复执行一遍上文的开头的命令即可。


<s>继续用原来的配置的方法</s>

配置文件里删掉最后一行的

Le_API='https://acme.zerossl.com/v2/DV90' 

再执行
>acme.sh --set-default-ca --server letsencrypt


### 自签名证书 

1.创建私钥

>openssl genrsa -out ojbk.key 1024

回车之后就可以得到一个私钥ojbk.key

2.创建证书签名请求

>openssl req -new -key ojbk.key -out ojbk.csr

回车之后会有一堆等着你输入的东西，直接一路回车。唯独一个Common Name要填成对应网站的IP或者域名：

Common Name (e.g. server FQDN or YOUR name) []:192.168.0.15

上面我直接填了机器的IP：192.168.0.15

3.创建自签名证书

>openssl x509 -req -in ojbk.csr -signkey ojbk.key -out ojbk.crt


回车后会得到一个自签名证书ojbk.crt

上面的有效期只有1个月 加上 -days 3650 整到10年

>openssl x509 -req -in ojbk.csr -out ojbk.crt -signkey ojbk.key -days 3650


使用
```

    http {
    ...
        server {
        listen      443 ssl;
        ssl_certificate         /usr/local/ssl/ojbk.crt;
        ssl_certificate_key     /usr/local/ssl/ojbk.key;
    ...
    }
    }

```
