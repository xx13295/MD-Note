### nginx 配置https 

     server {
                listen 443;
                server_name ojbk.plus www.ojbk.plus;

                ssl on;
                ssl_certificate      /usr/local/ssl/ojbk.plus/ojbk.plus.pem;
                ssl_certificate_key  /usr/local/ssl/ojbk.plus/ojbk.plus.key;

                access_log  logs/ojbk.access.log  main;
                error_log  logs/ojbk.error.log;

                proxy_set_header Host $host;
                proxy_set_header X-Forwarded-Host $host;
                proxy_set_header X-Forwarded-Server $host;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Requested-For $remote_addr;
                proxy_set_header REMOTE-HOST $remote_addr;

                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";


                location / {
                        proxy_pass http://127.0.0.1:8080;
                        proxy_connect_timeout 600;
                        proxy_read_timeout 600;
                }

        }

        server {
                listen       80;
                server_name  ojbk.plus www.ojbk.plus;
                return  301 https://ojbk.plus$request_uri;


        }