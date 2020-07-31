# 简介
	
	Screen是一款由GNU计划开发的用于命令行终端切换的自由软件。
	
	用户可以通过该软件同时连接多个本地或远程的命令行会话，并在其间自由切换。
	
	GNU Screen可以看作是窗口管理器的命令行界面版本。
	
	它提供了统一的管理多个会话的界面和相应的功能。
	
	官方网址：http://www.gnu.org/software/screen/

	在Screen环境下，所有的会话都独立的运行，并拥有各自的编号、输入、输出和窗口缓存。
	
	用户可以通过快捷键在不同的窗口下切换，并可以自由的重定向各个窗口的输入和输出。

# 安装

	yum install -y screen


# 语法

> screen [-AmRvx -ls -wipe][-d <作业名称>][-h <行数>][-r <作业名称>][-s ][-S <作业名称>]
 
	-A 　将所有的视窗都调整为目前终端机的大小。
	
	-d   <作业名称> 　将指定的screen作业离线。
	
	-h   <行数> 　指定视窗的缓冲区行数。
	
	-m 　即使目前已在作业中的screen作业，仍强制建立新的screen作业。
	
	-r   <作业名称> 　恢复离线的screen作业。
	
	-R 　先试图恢复离线的作业。若找不到离线的作业，即建立新的screen作业。
	
	-s 　指定建立新视窗时，所要执行的shell。
	
	-S   <作业名称> 　指定screen作业的名称。
	
	-v 　显示版本信息。
	
	-x 　恢复之前离线的screen作业。
	
	-ls或--list 　显示目前所有的screen作业。
	
	-wipe 　检查目前所有的screen作业，并删除已经无法使用的screen作业。
	
# 常用screen参数
	
	screen -S yourname -> 新建一个叫yourname的session
	
	screen -ls         -> 列出当前所有的session
	
	screen -r yourname -> 回到yourname这个session
	
	screen -d yourname -> 远程detach某个session
	
	screen -d -r yourname -> 结束当前session并回到yourname这个session

# 在Session下，使用ctrl+a(C-a) 
	C-a ? -> 显示所有键绑定信息
	
	C-a c -> 创建一个新的运行shell的窗口并切换到该窗口
	
	C-a n -> Next，切换到下一个 window 
	
	C-a p -> Previous，切换到前一个 window 
	
	C-a 0..9 -> 切换到第 0..9 个 window
	
	Ctrl+a [Space] -> 由视窗0循序切换到视窗9
	
	C-a C-a -> 在两个最近使用的 window 间切换 
	
	C-a x -> 锁住当前的 window，需用用户密码解锁
	
	C-a d -> detach，暂时离开当前session，将目前的 screen session (可能含有多个 windows) 
		
		丢到后台执行，并会回到还没进 screen 时的状态，此时在 screen session 里，
		
		每个 window 内运行的 process (无论是前台/后台)都在继续执行，即使 logout 也不影响。 
	
	C-a z -> 把当前session放到后台执行，用 shell 的 fg 命令则可回去。
	
	C-a w -> 显示所有窗口列表
	
	C-a t -> time，显示当前时间，和系统的 load 
	
	C-a k -> kill window，强行关闭当前的 window
	
	C-a [ -> 进入 copy mode，在 copy mode 下可以回滚、搜索、复制就像用使用 vi 一样
	
	    C-b Backward，PageUp 
	
	    C-f Forward，PageDown 
	
	    H(大写) High，将光标移至左上角 
	
	    L Low，将光标移至左下角 
	
	    0 移到行首 
	
	    $ 行末 
	
	    w forward one word，以字为单位往前移 
	
	    b backward one word，以字为单位往后移 
	
	    Space 第一次按为标记区起点，第二次按为终点 
	
	    Esc 结束 copy mode 
	
	C-a ] -> paste，把刚刚在 copy mode 选定的内容贴上
	
	
# 常用操作
	
	创建会话（-m 强制）：

>screen -dmS session_name

#### 关闭会话：

	session_name 可能重复 ，所以还可以用 session_name前面的id号 
	
	大致如下 有两个session_name 为 ojbk 其中一个id号是9554 另一个是15494
	
	[root@VM-4-2-centos mysql-8.0.21]# screen -ls
	There is a screen on:
        9554.ojbk       (Detached)
        15494.ojbk       (Detached)
	1 Socket in /var/run/screen/S-root.
	
	
	

>screen -X -S session_name quit

或者 

>screen -X -S id quit

#### 查看所有会话：

>screen -ls

#### 进入会话：

>screen -r session_name

或者

>screen -r id
