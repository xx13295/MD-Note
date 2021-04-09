@mode con cols=63 lines=40
@echo off
chcp 65001 >nul
title GIF转工具APNG v1.0 By 王小明

set "ffmpeg=ffmpeg"
set /A whitebgd = 1
set "PATH=%PATH%;"%~dp0""

if "%~1"=="" (
	cd /D "%~dp0"
) else (
	cd /D "%~f1"
)
echo.
mkdir temp >nul 2>nul
for /F %%i in ('dir /b^|findstr .gif$') do (
	echo 【正在处理 %%i 】
	echo. >nul
	if %whitebgd%==1 (
		REM 加白底输出帧。来自 esterTion
		%ffmpeg% -hide_banner -loglevel quiet -i "%%i" "temp\%%~ni_%%3d.gif" -f image2 -filter_complex "pad=iw*2:ih:iw:ih:white,crop=iw/2:ih:0:0[back];[back][0]overlay=0:0" >nul 2>nul
	) else (
		REM 保留通明输出帧。
		%ffmpeg% -hide_banner -loglevel quiet -i "%%i" "temp\%%~ni_%%3d.gif" -f image2 >nul 2>nul
	)
	
	cd temp
	REM 生成调色板。参考 https://gist.github.com/gka/148bbad67871fa6ca8d0b97e4eee94b5
	%ffmpeg% -i "%%~ni_%%3d.gif" -vf palettegen "%%~ni_palette.gif" >nul 2>nul
	REM 生成GIF
	%ffmpeg% -v warning -i "%%~ni_%%3d.gif" -i "%%~ni_palette.gif" -plays 0 -vf "setpts=PTS-STARTPTS" -y "..\%%~ni.apng" >nul 2>nul
	echo 【%%i 转换完成】
	cd ..
	ren "%%~ni.apng" "%%~ni.png" >nul 2>nul
	echo 【%%~ni.apng to %%~ni.png 重命名完成】
	echo.
	)
rd /s /q temp
echo 【全部文件处理完成，按任意键退出】
pause >nul
exit