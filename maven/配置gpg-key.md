# 配置GPG-KEY

```

<!-- GPG -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-gpg-plugin</artifactId>
    <version>1.5</version>
    <executions>
        <execution>
            <phase>verify</phase>
            <goals>
                <goal>sign</goal>
            </goals>
        </execution>
    </executions>
</plugin>

```




下载gpg4win：

https://files.gpg4win.org/gpg4win-3.1.15.exe



>gpg --gen-key

来生成密钥，需要输入名字和邮箱，并弹出一个对话框来输入密钥的保护密码，一定要记住，后面会用到

> gpg --list-keys


查看生成的密钥，得到类似如下的输出，敏感部分打了掩码，pub就是生成的公钥

>gpg --keyserver hkp://pool.sks-keyservers.net --send-keys 0276B66ACF24A*****D69225F2323

>gpg --keyserver hkp://pool.sks-keyservers.net --recv-keys 0276B66ACF24A*****D69225F2323


POM 

```

    <distributionManagement>
        <snapshotRepository>
            <!--oss需要对应到settings.xml下的service的id-->
            <id>sonatype-nexus-snapshots</id>
            <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
        </snapshotRepository>
        <repository>
            <id>sonatype-nexus-releases</id>
            <url>https://s01.oss.sonatype.org/content/repositories/releases/</url>
        </repository>
    </distributionManagement>

```

settings.xml

```
    <server>
      <id>sonatype-nexus-releases</id>
      <username>user</username>
      <password>pass</password>
    </server>
	
	<server>
      <id>gpg.passphrase</id>
      <passphrase>你的密钥</passphrase>
    </server>


```


>mvn clean deploy -P sonatype-oss-release
