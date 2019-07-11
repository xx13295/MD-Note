#!/bin/bash
#
#Author: wxm
#关闭操作使用的是kill -9
#
#脚本存放目录
APP_HOME=/home/wxm/frp-0.27.0
#脚本启动文件
APP_MAIN=frps
#脚本配置文件
APP_CFG=frps.ini

psid=0
 
checkpid() {
   shpid=`ps aux|grep $APP_MAIN|grep -v 'grep'`
   if [ -n "$shpid" ]; then   
     psid=`echo $shpid | awk '{print $2}'`
   else
      psid=0
   fi
}
start() {
   checkpid
   if [ $psid -ne 0 ]; then
      echo "================================"
      echo "warn: $APP_MAIN already started! (pid=$psid)"
      echo "================================"
   else
      echo -n "Starting $APP_MAIN ..."
      $APP_HOME/$APP_MAIN -c $APP_HOME/$APP_CFG &
      checkpid
      if [ $psid -ne 0 ]; then
         echo "(pid=$psid) [OK]"
      else
         echo "[Failed]"
      fi
   fi
}
 
stop() {
   checkpid
 
   if [ $psid -ne 0 ]; then
      echo -n "Stopping $APP_MAIN ...(pid=$psid) "
      kill -9 $psid
      if [ $? -eq 0 ]; then
         echo "[OK]"
      else
         echo "[Failed]"
      fi
 
      checkpid
      if [ $psid -ne 0 ]; then
         stop
      fi
   else
      echo "================================"
      echo "warn: $APP_MAIN is not running"
      echo "================================"
   fi
}
 
status() {
   checkpid
 
   if [ $psid -ne 0 ];  then
      echo "$APP_MAIN is running! (pid=$psid)"
   else
      echo "$APP_MAIN is not running"
   fi
}

case "$1" in
   'start')
      start
      ;;
   'stop')
     stop
     ;;
   'restart')
     stop
     start
     ;;
   'status')
     status
     ;;
  *)
     echo "Usage: $0 {start|stop|restart|status}"
     exit 1
esac
exit 0
