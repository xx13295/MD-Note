# ffmpeg
### 这是个开源的软件 可以帮助我们对音频视频的文件进行各种转换切割操作
	
	以下 示例仅提供 mp3比特率转换demo
	
	更多 操作   请 使用 度娘


----------------------------
	食用方法          linux
----------------------------

### fmmpeg 下载地址
### https://ffmpeg.org/releases/ffmpeg-4.0.tar.bz2
	本教程使用上诉地址作为示范 为当前最新版本
### 官方下载地址  http://ffmpeg.org/download.html 

### lame （mp3编码器） 下载地址  https://jaist.dl.sourceforge.net/project/lame/lame/3.100/lame-3.100.tar.gz

### 官方地址历史版本  https://sourceforge.net/projects/lame/files/lame/

### 还得准备一下 yasm http://www.tortall.net/projects/yasm/releases/yasm-1.3.0.tar.gz

	使用 rz 上传 ffmpeg-4.0.tar.bz2   3.100/lame-3.100.tar.gz  yasm-1.3.0.tar.gz
	//如果没安装 就安装一下yum install lrzsz 
	//rz 上传 sz 下载 还蛮方便
	
	//注意 一定要 1.3.0 版本的。 1.2.0的版本过低了 貌似 ffmpeg 3.4以后的版本就已经不支持啦
	//编译的时候会提示yasm版本过低
	
	
### 依次解压编译安装 

#### yasm 安装
	tar -zxvf  yasm-1.3.0.tar.gz
	cd yasm-1.2.0
	./configure
	make
	sudo make install
	
#### mp3编码器 安装

	tar -zxvf lame-3.100.tar.gz  
	cd lame-3.100 
	./configure 
	make 
	sudo make install
	
#### ffmpeg 安装
	
	bzip2 -d ffmpeg-4.0.tar.bz2
	tar xvf ffmpeg-4.0.tar
	
	cd ffmpeg-4.0/
	./configure  --enable-libmp3lame 
	make 
	sudo make install
	
#### 在 ~/.bashrc 或者 ~/.bash_profile 中加入环境变量 

	cd 
	vi .bash_profile
	
	加入 export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/lib
	
	Esc ：wq
	
	//生效环境变量
	source .bash_profile 
	
	//让动态链接库为系统所共享
	sudo ldconfig

###  这时候 我们输入 ffmpeg 可以查看当前版本 以及使用的编码器 

	[ojbk@VM_0_11_centos ~]$ ffmpeg 
	ffmpeg version 4.0 Copyright (c) 2000-2018 the FFmpeg developers
	  built with gcc 4.8.5 (GCC) 20150623 (Red Hat 4.8.5-28)
	  configuration: --enable-libmp3lame
	  libavutil      56. 14.100 / 56. 14.100
	  libavcodec     58. 18.100 / 58. 18.100
	  libavformat    58. 12.100 / 58. 12.100
	  libavdevice    58.  3.100 / 58.  3.100
	  libavfilter     7. 16.100 /  7. 16.100
	  libswscale      5.  1.100 /  5.  1.100
	  libswresample   3.  1.100 /  3.  1.100
	Hyper fast Audio and Video encoder
	usage: ffmpeg [options] [[infile options] -i infile]... {[outfile options] outfile}...
	
	Use -h to get full help or, even better, run 'man ffmpeg'
	
###  基本使用 
 
	随便 上传一个 音乐 文件  使用 ffmpeg -i 文件名 可以查看 该音乐的详细信息 
	
	ffmpeg -i 'Waiting For Love.mp3' 
	
	
	转换 这个音乐文件比特率为 320  我现在想转换成 128 
	ffmpeg -y -i 'Waiting For Love.mp3' -acodec libmp3lame -ab 128k -f mp3 'new file.mp3'
	
	
	
	比特率 降低了 文件大小自然就 变小了。  原本 10M  现在 只有 4M 
	
	
	只需要安装更多的 编码器 就能支持更多 转换操作 
	
	
	安装新的 解码器后 从新安装下 ffmpeg 即在 ./configure后加入--enable-[插件]
	例如：
	
	 ./configure  --enable-libmp3lame --enable-libvorbis --enable-gpl --enable-version3 --enable-nonfree --enable-pthreads --enable-libfaac --enable-libopencore-amrnb --enable-libopencore-amrwb --enable-libx264 --enable-libxvid --enable-postproc --enable-ffserver --enable-ffplay
	make 
	sudo  make install 
	
----------------------------
	食用方法        windows
----------------------------
	
	
	下载window版  直接解压 使用命令就完事 了哦 
	
	win + R
	
	cmd  
	
	D: 
	
	cd  D:\ffmpeg\bin
	
	ffmpeg 
	
	可以看出 windows 版的内置很多的编码器了直接用 就完事了。 
	
![image](https://github.com/xx13295/wxm/blob/master/images/ffmpeg/ffmpeg1.png?raw=true)
	 
	