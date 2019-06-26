# linux服务器上的压缩包的常用命令

.tar 解包 tar xvf filename.tar
.tar 打包 tar cvf filename.tar dirname

.gz 解压1 gunzip filename.gz
.gz 解压2 gzip -d filename.gz
.gz 压缩 gzip filename

.tar.gz 和 .tgz 解压 tar zxvf filename.tar.gz
.tar.gz 和 .tgz 压缩 tar zcvf filename.tar.gz dirname

.bz2 解压1 bzip2 -d filename.bz2
.bz2 解压2 bunzip2 filename.bz2
.bz2 压缩 bzip2 -z filename

.tar.bz2 解压 tar jxvf filename.tar.bz2
.tar.bz2 压缩 tar jcvf filename.tar.bz2 dirname

.bz 解压1 bzip2 -d filename.bz
.bz 解压2 bunzip2 filename.bz
.tar.bz 解压 tar jxvf filename.tar.bz

.z 解压 uncompress filename.z
.z 压缩 compress filename

.tar.z 解压 tar zxvf filename.tar.z
.tar.z 压缩 tar zcvf filename.tar.z dirname

.zip 解压 unzip filename.zip
.zip 压缩 zip filename.zip dirname

.rar 解压 rar x filename.rar
.rar 压缩 rar a filename.rar dirname

lzop工具最适合在注重压缩速度的场合，压缩文件时会新建.lzo文件，而原文件保持不变(使用-U选项除外)

lzop -v test 创建test.lzo压缩文件，输出详细信息，保留test文件不变

lzop -Uv test 创建test.lzo压缩文件，输出详细信息，删除test文件

lzop -t test.lzo 测试test.lzo压缩文件的完整性

lzop –info test.lzo 列出test.lzo中各个文件的文件头

lzop -l test.lzo 列出test.lzo中各个文件的压缩信息

lzop –ls test.lzo 列出test.lzo文件的内容，同ls -l功能

cat test | lzop > t.lzo 压缩标准输入并定向到标准输出

lzop -dv test.lzo 解压test.lzo得到test文件，输出详细信息，保留test.lzo不变

注：lzop没有unlzop命令，只能加上-d选项解压，向lzop传入一组文件和目录名时，lzop会压缩所有文件但是会忽略目录，
压缩文件保留原来文件的权限设置和时间戳