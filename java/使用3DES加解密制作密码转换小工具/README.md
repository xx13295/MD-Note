# 加解密小工具！!

	1.password-converter     源代码
	2.pw-conver.jar          打好的jar包
	3.converter              shell脚本 
	

###  准备工作
	上传 pw-conver.jar 到/home/ojbk/tools/
	这里的 ojbk 为你的 用户名  没有tools 目录你可以 创建一个（使用 命令  mkdir tools ）
	上传 converter 脚本 到任意 你喜欢的地方 
	这里我选择 放在 bin目录中 /home/ojbk/bin/
	
### 赋予 脚本执行权限 
	
	chmod 755 converter
	
	
### 食用方法

####加密
	converter -e ojbk
	
	得到结果 ：aDTVdaFpzQI=
####解密
	converter -d aDTVdaFpzQI=
	
	得到结果 ：ojbk
	
	
		
	
	
	