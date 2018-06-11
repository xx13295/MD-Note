###Let's Encrypt 宣布 ACME v2 正式支持通配符证书。Let's Encrypt 宣称将继续清除 Web 上采用 HTTPS 的障碍，让每个网站轻松获取管理证书。

####  acme.sh 
[传送门1](https://github.com/Neilpang/acme.sh/wiki/%E8%AF%B4%E6%98%8E/)

  
#### 参考 该博主
[传送门2](https://my.oschina.net/kimver/blog/1634575/)


### 执行以下命令 

	curl https://get.acme.sh | sh	
	source ~/.bashrc

#### 导入阿里云后台的密钥 [获取key](https://ak-console.aliyun.com/#/accesskey/)

	export Ali_Key="dsadhasdkjaskdjsds"
	export Ali_Secret="dsfhsfsfksfsldfsfds"

#### 填写自己的域名
	acme.sh --issue --dns dns_ali -d ojbk.plus -d *.ojbk.plus
	
#### 在证书生成目录执行
	acme.sh --installcert -d ojbk.plus -d *.ojbk.plus  \
	--keypath       /usr/local/ssl/ojbk/ojbk.plus.key  \
	--fullchainpath /usr/local/ssl/ojbk/ojbk.plus.pem
	
	//这样就会把key和pem生成到指定的目录   
	
	当然 首先 你 你先 进入 到 usr/local/    使用 mkdir 命令创建文件夹 
	创建 ssl 文件 夹、再 进入ssl文件夹中 创建ojbk 文件夹 
	注意 将 权限修改一下 
	修改文件的所有者和组 【组:用户】
	chown -R ojbk:ojbk /file  



##  好了 ，这样就拥有了免费的ssl 证书 三个月一次 他会自动 帮你重新 更新证书的
