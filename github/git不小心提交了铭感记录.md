#如何删除github commit记录

>git reset --soft HEAD~i

    i代表要恢复到多少次提交前的状态，
    如指定i = 2则恢复到最近两次提交前的版本
    --soft代表只删除服务器记录，不删除本地

>git push origin master --force


然后输入自己的 github用户名

加 token令牌就完事了