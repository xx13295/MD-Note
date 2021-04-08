# Apng 

    apng格式诞生于2004年，是基于png格式的动画格式图片，
    它的动图后缀依然是.png，apng格式相对GIF格式有更多的优势，
    在色彩方面它完美支持1600万种颜色，
    对于渐变透明的元素，有着非常优秀的成像效果.

    预览apng动图需要拖拽到浏览器中才可以查看动画，
    所以传播性没有GIF图强。


# gif 2 apng

> ffmpeg.exe -y -i "L02.gif" -plays 0  -vf "setpts=PTS-STARTPTS" 232.apng



>ffmpeg.exe -y -i "GIF.gif" -plays 0  -vf "setpts=PTS-STARTPTS, hqdn3d=1.5:1.5:6:6" -r 20 232.apng


    转完改下后缀 png 完事