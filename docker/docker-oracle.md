# oracle 

    未测试


>docker pull registry.cn-hangzhou.aliyuncs.com/woqutech/oracle-database-11.2.0.4.0-ee

```

docker run -d --name oracledb \
-p 10136:1521 \
-e ORACLE_SID=orcl \
-e ORACLE_PWD=oracle \
-e ORACLE_CHARACTERSET=ZHS16GBK \
-e SGA_SIZE=8G \
-e PGA_SIZE=8G \
-e DB_ROLE=primary \
-e ENABLE_ARCH=true \
-v /home/oracledata/data:/opt/oracle/oradata \
registry.cn-hangzhou.aliyuncs.com/woqutech/oracle-database-11.2.0.4.0-ee


```


```

 sqlplus /nolog
 
 conn /as sysdba
 
 
 alter user system identified by 123456;
 
 alter user sys identified by 123456;
 
 alter user scott identified by 123456;
 alter user scott account unlock;
 
 ALTER PROFILE DEFAULT LIMIT PASSWORD_LIFE_TIME UNLIMITED;
 

```
