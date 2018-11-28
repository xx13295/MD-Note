# 伪回收站功能

	麻麻再也不用担心我 不小心删错文件了 。
	
	原理： 就是 将要删除的文件 移动到 某一个 文件夹中。 
	

## 第一步 创建一个 文件夹 当做 回收站 

>mkdir -p ~/.Ojbk


## 第二步 配置 当前用户专用.bashrc文件

>vi ~/.bashrc

	按 一下 键盘的     i  键  开始写入 下面的一大串 内容    写完后 Esc : wq 保存
	
	
	alias rm=mvfile        
	alias lss='ls ~/.Ojbk'  
	alias mvv=undel
	alias del=delfile 
	alias delall=delallfile 
	 
	mvfile()  
	{  
	  mv $@ ~/.Ojbk/`date +"%Y%m%d-%H:%M:%S"`-$@ 
	}
	undel()  
	{  
	  mv -i ~/.Ojbk/$@ ./  
	} 
	delfile()  
	{  
	    read -p "are you sure?[y/n]" confirm  
	    [ $confirm == 'y' ] || [ $confirm == 'Y' ]  && /usr/bin/rm -rf ~/.Ojbk/$@ 
	}
	delallfile()  
	{  
	    read -p "are you sure?[y/n]" confirm  
	    [ $confirm == 'y' ] || [ $confirm == 'Y' ]  && /usr/bin/rm -rf ~/.Ojbk/*  
	}


## 第三步 让配置生效 

>source ~/.bashrc


### 食用 方法
|参数|作用|
|:-|:-|
|rm|删除文件到回收站 （参数为要删除的文件名） |
|mvv|还原回收站的文件到用户目录下（参数为回收站中文件名）|
|lss|列出回收站中被你伪删除的文件（无参）|
|del|递归删除某个文件（参数为回收站中文件名）|
|delall|删除回收站里全部文件（无参）|

###  可以配合的定时清理文件食用 

	这样 删除到回收站里的 文件定期就会自动删除 ~
![image](https://github.com/xx13295/wxm/blob/master/images/o.png?raw=true)
[传送门](https://github.com/xx13295/CodingNote/tree/master/linux%E5%AE%9A%E6%97%B6%E6%B8%85%E7%90%86%E6%96%87%E4%BB%B6)	
