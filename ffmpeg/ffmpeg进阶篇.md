#使用 FFmpeg 进行视频转码       

	比如 我们要讲mp4的视频换成 mkv 格式的  -i 参数是 转换文件必须的
	可以使用命令  ffmpeg -i 【输入文件】 【输出文件】
	
	加入 -y 参数就是如果 ojbk.mkv已经存在会覆盖掉，不然他会提示你文件以及存在了你需要手动输入y才能继续执行
	
	ffmpeg -y -i d:/ojbk.mp4 d:/ojbk.mkv
	
	发现 转换完成后 视频 丢失 了音频源 因为 没有指定音频编码 mp4默认使用 aac音频编码格式 那么我们 也给 输出文件指定这个编码
	【这里的-c:a 是   -acodec的简写 】
	ffmpeg -y -i d:/ojbk.mp4 -c:a aac d:/ojbk.mkv
	
	发现 执行失败 报出错误 
	The encoder 'aac' is experimental but experimental codecs
	are not enabled, add '-strict -2' if you want to use it.
	
	提示已经很明显了 缺少参数  我们 增加 '-strict -2' 再试一次  
	
	ffmpeg -y -i d:/ojbk.mp4 -c:a aac -strict -2 d:/ojbk.mkv
	
	转换完成后打开视频 听到了 久违的声音。
	
	还可以指定 视频编码 -c:v hevc  这时候 这个视频转换就会使用 hevc编码  。【这里的-c:v 是   -vcodec的简写 】
	
	ffmpeg -y -i d:/ojbk.mp4 -c:v hevc -c:a aac -strict -2 d:/ojbk.mkv
	
### 将视频尺寸转换为 `640x360` 由于视频的尺寸发生了改变因此对视频进行重新编码，但音频不用。
	
	ffmpeg -y -i  d:/ojbk.mp4 -c:v h264 -c:a copy -s 640x360 d:/newojbk.mp4
	
### 单纯的转换格式 可以 直接使用 -c copy
	
	ffmpeg -y -i d:\ojbk.mp4 -c copy d:\ojbk.mkv
	
	
### 把视频的 前10s去掉 
	ffmpeg -y -i d:\ojbk.mp4 -c copy -ss 10 d:\newojbk.mp4
	
### 截取一段视频 从这个视频的 3分25秒开始  到 4分 25秒 也就是 一分钟的视频    -ss 表示 开始时间   -t表示 持续时间  

	ffmpeg -y -i d:\ojbk.mp4 -c copy -ss 03:25 -t 1:00 d:\newojbk.mp4
	

## 能分割就能应该能组合  --> 将两段小视频组合
	首先在 D盘 创建 一个 ojbk.txt
	
	在ojbk.txt中写入
	
	file newojbk1.mp4
	file newojbk2.mp4
	
	保存
	
	执行以下命令 不出意外  就是 按照你写的顺序开始拼接的一段新视频  newojbk3
	ffmpeg -f concat -i d:/ojbk.txt -c copy d:/newojbk3.mp4	

## 视频提取音频流视频流 除去视频中带英文字母广告信息等
	
	ffmpeg -y -i Parasite.2019.KOREAN.1080p.WEBRip.x264.AAC2.0-NOGRP.mkv -map 0:0 -map 0:1 -c:v h264 -c:a copy jishengchong.mp4
	
	加上自己的中文字幕
	
	ffmpeg -y -i jishengchong.mp4 -vf subtitles=PARASITE.2019.720p.HDRip.H264.AAC-NonDRM.ass jishengc.mp4

	服务器后台运行 就使用 
	nohup ffmpeg {xxx省略xxx} 1>ffmpeg.log 2>&1 &
	
	