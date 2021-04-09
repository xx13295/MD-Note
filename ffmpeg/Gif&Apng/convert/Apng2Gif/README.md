# 使用方式：
	
	设置ffmpeg环境变量
	在Path  中加入 
	D:\ffmpeg\bin;
	直接将png文件/文件夹 拖进 .bat 文件里 就可以进行转换
	

### 注意事项：

如果 将图片 拉入 .bat 文件中后 显示乱码 没有正确 执行 一般情况下是 
win10 命令提示符：mode不是内部或外部命令
其实就是没有配置环境变量 

在Path  中加入 

> %SystemRoot%\system32;%SystemRoot%;%SystemRoot%\System32\Wbem;

有的时候  会卡在正在处理 按回车健试试