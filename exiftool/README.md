# Exiftool介绍

    官网：https://exiftool.org/

    ExifTool由Phil Harvey开发，是一款免费、跨平台的开源软件
    用于读写和处理图像（主要）、音视频和PDF等文件的元数据（metadata）
    ExifTool可以作为Perl库（Image::ExifTool）使用，也有功能齐全的命令行版本
    ExifTool支持很多类型的元数据，包括
    Exif、IPTC、XMP、JFIF、GeoTIFF、ICC配置文件、Photoshop IRB、FlashPix、AFCP和ID3
    以及众多品牌的数码相机的私有格式的元数据。

### 什么是Exif

    Exif是可交换图像文件格式（Exchangeable image file format）
    是一种标准，定义了与数码相机捕获的图像（或其他媒体）有关的信息
    用于存储重要的数据，比如相机的曝光、拍摄日期和时间，甚至GPS定位等。
    在早期，摄影师需要随身携带笔记本来记录重要信息，如日期、快门速度、光圈等，
    这非常麻烦而且容易出错。
    如今，每台数码相机都支持Exif，
    能够将拍摄时的很多参数通过这种格式（Exif）记录到照片中，
    这些照片（或其他类型的文件）中的额外数据就叫元数据（metadata），
    它由一系列参数组成，如快门速度、光圈、白平衡、相机品牌和型号、镜头、焦距等等。
    Exif信息可能会造成隐私泄露（相机型号、位置等），
    在社会工程学中，Exif也是获取目标信息的一种手段，
    所以建议在把照片上传到互联网之前先清理Exif数据。

### ExifTool使用示例

    几个常用的参数

    -r：递归处理子目录
    -overwrite_original：不备份_original文件，直接覆盖
    -restore_original：恢复备份
    -delete_original：删除备份

    读取文件a.jpg的所有元数据
    exiftool a.jpg
    
    写入标签artist、值ojbk到文件a.jpg(如果artist已存在将更新其值)
    exiftool -artist=ojbk a.jpg
    exiftool -artist=ojbk a.jpg b.jpg c.jpg(同时写入多个文件)
    exiftool -artist=ojbk D:/images(写入目录中所有文件)
    exiftool -artist=ojbk D:/images -r(递归处理子目录)
    exiftool -artist="ojbk" -copyright="wxm" a.jpg(同时写入多个标签)
    
    将创建时间、光圈、快门速度和ISO四项以列表形式保存为out.txt
    exiftool -T -createdate -aperture -shutterspeed -iso DIR > out.txt
    
    打印某照片的尺寸和曝光时间
    exiftool -s -ImageSize -ExposureTime a.jpg
    
    递归扫描某目录所有照片，将共有的元数据写入相同文件名的txt文件中
    exiftool -r -w .txt -common DIR
    
    生成image.raw的缩略图thumbnail.jpg
    exiftool -b -ThumbnailImage image.raw > thumbnail.jpg
    
    从a.jpg提取完整的xmp数据记录
    exiftool -xmp -b a.jpg> out.xmp
    
    递归删除某目录下所有文件的全部元数据
    exiftool -all= -r DIR

>摘录https://www.rmnof.com/article/exiftool-introduction/



### 使用

    有些相机会记录拍照时的GPS定位信息。如果你不希望别人看到使用该命令删除gps信息
    exiftool -gps:all= photo.jpg

    删除所有信息
    exiftool -all= photo.jpg 

    删除EXIF以外的所有信息
    exiftool -all= --exif:all photo.jpg 

    这个命令显示指定文件的metadata的属性,当不能准确的获取exif信息
    mdls  photo.jpg 


## 乱码问题

    exiftool -charset filename=utf8 -codedcharacterset=utf8 -v  你好.jpg 


     * https://exiftool.org/faq.html#Q10
     
     * https://exiftool.org/exiftool_pod.html#WINDOWS-UNICODE-FILE-NAMES
