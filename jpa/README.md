# QueryDsl

	# Maven
		<dependency>
		    <groupId>com.querydsl</groupId>
		    <artifactId>querydsl-jpa</artifactId>
		    <version>4.2.1</version>
		</dependency>
	
		<dependency>
		    <groupId>com.querydsl</groupId>
		    <artifactId>querydsl-apt</artifactId>
		    <version>4.2.1</version>
		    <scope>provided</scope>
		</dependency>
		<plugin>
			<groupId>com.mysema.maven</groupId>
			<artifactId>apt-maven-plugin</artifactId>
			<version>1.1.3</version>
			<executions>
				<execution>
					<goals>
						<goal>process</goal>
					</goals>
					<configuration>
						<outputDirectory>target/generated-sources/java</outputDirectory>
						<processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
					</configuration>
				</execution>
			</executions>
		</plugin>



#### 查询类的生成	   

	# 需要通过 apt-maven-plugin 插件和querydsl-apt配合来生成检索类
		
		* 不同Entity注解可以使用不同的生成策略(<processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>)
			com.querydsl.apt.jpa.JPAAnnotationProcessor					//jpa的注解
			com.querydsl.apt.hibernate.HibernateAnnotationProcessor		//Hibernate的注解
		
		* 使用maven命令: mvn compile -DskipTests
		
		* 会在目录: target/generated-sources/java 生成与entity同包的查询类(类名都添加了Q字符)  【由上方 maven plugin 中定义的目录】

	