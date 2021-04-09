@mode con cols=63 lines=40
@echo off
chcp 65001 >nul
title GIF转工具APNG v1.1 By 王小明

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
		copy "%%i" "temp\%%~ni_%%3d.gif" >nul 2>nul
	) else (
		%ffmpeg% -hide_banner -loglevel quiet -i "%%i" "temp\%%~ni_%%3d.gif" -f image2 >nul 2>nul
	)
	cd temp
	REM 生成APNG
	%ffmpeg% -v warning -i "%%~ni_%%3d.gif"  -plays 0 -vf "setpts=PTS-STARTPTS,fps=fps=15" -y "..\%%~ni.apng" >nul 2>nul
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