#  docker-nexus3

    docker 构建一个Maven私服


1.拉取nexus3

>docker pull sonatype/nexus3

2.创建数据目录 根据你自己的需求变更路径

>mkdir -p /usr/local/nexus3/nexus-data  && chown -R 200 /usr/local/nexus3/nexus-data

3.启动nexus

>docker run -d -p 8081:8081 --privileged=true --name nexus -v /usr/local/nexus3/nexus-data:/nexus-data  3aa3

4.  设置nexus3的默认密码

>cd /usr/local/nexus3/nexus-data 

>cat admin.password 

    会得到一串 密码  复制即可

    开浏览器访问 http://ip:8081/

    右上角登录 输入 账号 admin
    密码 刚刚复制的那一串

    然后重置密码即可

5. 默认仓库说明

maven-central：maven中央库，默认从https://repo1.maven.org/maven2/拉取jar

maven-releases：私库发行版jar，初次安装请将Deployment policy设置为Allow redeploy

maven-snapshots：私库快照（调试版本）jar

maven-public：仓库分组，把上面三个仓库组合在一起对外提供服务，在本地maven基础配置settings.xml或项目pom.xml中使用

仓库类型    

Group：这是一个仓库聚合的概念，用户仓库地址选择Group的地址，即可访问Group中配置的，用于方便开发人员自己设定的仓库。maven-public就是一个Group类型的仓库，内部设置了多个仓库，访问顺序取决于配置顺序，3.x默认Releases，Snapshots，                                  Central，当然你也可以自己设置。

Hosted：私有仓库，内部项目的发布仓库，专门用来存储我们自己生成的jar文件

3rd party：未发布到公网的第三方jar (3.x去除了)

Snapshots：本地项目的快照仓库

Releases： 本地项目发布的正式版本

Proxy：代理类型，从远程中央仓库中寻找数据的仓库（可以点击对应的仓库的Configuration页签下Remote Storage属性的值即被代理的远程仓库的路径），如可配置阿里云maven仓库

Central：中央仓库

Apache Snapshots：Apache专用快照仓库(3.x去除了)


6.增加代理源

点击设置那个图标 --> Repositories --> Create repository --> maven2 (proxy) 


Name 填写 aliyun

Remote storage: 填写 http://maven.aliyun.com/nexus/content/groups/public

Not found cache TTL: 修改为 288000

保存完事 以此类推

```

1. aliyun
http://maven.aliyun.com/nexus/content/groups/public
2. apache_snapshot
https://repository.apache.org/content/repositories/snapshots/
3. apache_release
https://repository.apache.org/content/repositories/releases/
4. atlassian
https://maven.atlassian.com/content/repositories/atlassian-public/
5. central.maven.org
http://central.maven.org/maven2/
6. datanucleus
http://www.datanucleus.org/downloads/maven2
7. maven-central （安装后自带，仅需设置Cache有效期即可）
https://repo1.maven.org/maven2/
8. nexus.axiomalaska.com
http://nexus.axiomalaska.com/nexus/content/repositories/public
9. oss.sonatype.org
https://oss.sonatype.org/content/repositories/snapshots
10.pentaho
https://public.nexus.pentaho.org/content/groups/omni/


```

7. 设置maven-public 将这些代理加入Group 

点击设置那个图标 --> Repositories --> maven-public
也可以直接使用下面的链接
http://nexus3-host:8081/#admin/repository/repositories:maven-public

找到 Member repositories: 

把我们自己添加的代理移动到左边的 Members 

建议 把 aliyun 置顶 然后 保存完事



6.在我们maven的settings.xml增加部分配置 

    注意 nexus3-host 这个是我在hosts文件 中配置了hosts
    如果没有配置默认使用ip即可
    

```

  <servers>
    <server>
      <id>nexus-releases</id>
      <username>admin</username>
      <password>admin123</password>
    </server>
    
    <server>
      <id>nexus-snapshots</id>
      <username>admin</username>
      <password>admin123</password>
    </server>
  </servers>

  <mirrors>
    <mirror>
      <id>OjbKMirror</id>
      <mirrorOf>*</mirrorOf>
      <name>OjbK Repository Mirror.</name>
      <url>http://nexus3-host:8081/repository/maven-public/</url>
    </mirror>
  </mirrors>
  
  <profiles>
    <profile>
      <id>OjbK</id>
      <repositories>
        <repository>
          <id>nexus</id>
          <name>Public Repositories</name>
          <url>http://nexus3-host:8081/repository/maven-public/</url>
          <releases>
            <enabled>true</enabled>
          </releases>
        </repository>
      
        <repository>
          <id>central</id>
          <name>Central Repositories</name>
          <url>http://nexus3-host:8081/repository/maven-central/</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
        </repository>
        
        <repository>
          <id>release</id>
          <name>Release Repositories</name>
          <url>http://nexus3-host:8081/repository/maven-releases/</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
        </repository>
        
        <repository>
          <id>snapshots</id>
          <name>Snapshot Repositories</name>
          <url>http://nexus3-host:8081/repository/maven-snapshots/</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>
      
      <pluginRepositories>
        <pluginRepository>
          <id>plugins</id>
          <name>Plugin Repositories</name>
          <url>http://nexus3-host:8081/repository/maven-public/</url>
        </pluginRepository>
      </pluginRepositories>
    </profile>
  </profiles>
  
  
  
```

7. maven 项目的 pom文件中加入 

```

 <distributionManagement>
        <repository>
            <id>nexus-releases</id>
            <name>Nexus Release Repository</name>
            <url>http://nexus3-host:8081/repository/maven-releases/</url>
        </repository>
        <snapshotRepository>
            <id>nexus-snapshots</id>
            <name>Nexus Snapshot Repository</name>
            <url>http://nexus3-host:8081/repository/maven-snapshots/</url>
        </snapshotRepository>
    </distributionManagement>

```

这样就可以使用 mvn deploy 上传到私服了



8. 注意事项

明明配置了 以上内容 deploy 却报 401无权限 
检查默认的settings.xml 是否已经配置过以上参数

>mvn help:effective-settings

如果 idea 底部的Terminal 执行失败 可尝试 左侧的maven插件使用Lifecycle中deploy



