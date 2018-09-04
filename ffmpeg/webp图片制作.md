# Webp 图片格式

	说起这个webp 我8012年 也是第一次听说，我见不多识不广，但我会百度。
	
	WebP是Google推出的影像技术，它可让网页图档有效进行压缩，
	
	同时又不影响图片格式兼容与实际清晰度，进而让整体网页下载速度加快。
	
	总之 ，牛逼~
	
	（Ie 浏览器现在不支持的~）
	
	
# 使用 ffmpeg 获取webp 格式图片 

	动图静图格式都是webp

## 举个栗子 
	
	在windows环境下  解压就能用 
	
	linux 的话 ffmpeg 在编译时需要加 --enable-libwebp 
	
	ffmpeg 编码时两个参数说明
	-lossless 无损压缩规范。默认值：0（1是可逆压缩）
	-qscale [0~100]关系到图片的质量。默认值为75。
	
	以下将一张图 1.jpg 转换为 webp 的ffmpeg 命令
	
>ffmpeg -y -i 1.jpg -vcodec libwebp -qscale 90 1.webp

	上面的是有损压缩 ，下面是 无损压缩
	
>ffmpeg -y -i 1.jpg -vcodec libwebp -lossless 1 2.webp


## 调整大小 

	如果需要调整大小 需要 增加参数 
	
	-vf scale=w:h
	
	其中w是宽度，h是长度。默认都是-1，就是不做修改。
	
	等比调整 只需要修改其中一个为具体的值 另一个为-1即可。
	自定义比例当然是 自己设定 w 和 h 的值啦~
	还可以设置成原图尺寸的一个计算公式，比如-vf scale=iw/2:ih/2。

## 视频转webp

	-ss 02:30 -t 10             【 截取这个 mp4从2分30s开始 截10s】 
	
	-loop 0                                      【代表 循环播放】
	
	preset
				预置：默认为default
				none
				不使用任何预设
				default
				自动指定
				picture
				肖像照片
				photo
				风景照片
				drawing
				绘制
				icon
				多彩与小尺寸
				text
				文本 字符居中
	

>ffmpeg -y -i ojbk.mp4 -vcodec libwebp -qscale 60 -ss 02:30 -t 10 -preset default -loop 0 -vf scale=640:-1,fps=15 -an -vsync 0 ojbk.webp
