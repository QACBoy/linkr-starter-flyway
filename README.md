# linkr-starter-flyway

> 文章连接：[|- flyway -| 带你快速了解与使用 flyway](https://blog.csdn.net/QAC_Boy/article/details/102856848) 

### flyway 引入
其他几种引入方式，详见 [|- flyway -| 项目中接入 flyway 的几种方式](https://blog.csdn.net/QAC_Boy/article/details/102692055) ，以下以 自定义 starter 为例

1. #### pom 文件引入 flyway 相应依赖

	**starter 和接入项目在文末也会给出相应的项目连接地址**
	```xml
	<dependency>
    	 <groupId>org.flywaydb</groupId>
     	<artifactId>flyway-core</artifactId>
     	<version>6.0.7</version>
	</dependency>
	<!-- 引入自定义的 flyway starter -->
	<dependency>
    	<groupId>com.linkr</groupId>
    	<artifactId>linkr-starter-flyway</artifactId>
   	 <version>1.0.0-SNAPSHOT</version>
	</dependency>
	```



3. #### yml 文件中的配置如下

   **配置中心的配置信息：**
   ```yml
   flywaydb:
     url: ${spring.datasource.url}
     username: username
     password: password
   ```

   **注意**：

   要求数据库账号 除了拥有正删改查操作外还需拥有能够**操作数据表**的权限，因为第一次引进 flyway的时候，会在数据库中创建一张名为 `flyway_schema_history` 的数据表，该表主要用于记录 SQL 脚本的执行记录和版本信息

   url： 直接引用 当前项目中的数据库连接即可
   username： 配置成加密后的字符串
   password： 配置成加密后的字符串

   

4. #### 添加数据库脚本

   **脚本存放位置**（默认）：src/resources/db/migration（目录是可以自定义，参数配置见 使用须知 ）
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191101143455289.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1FBQ19Cb3k=,size_16,color_FFFFFF,t_70)

**SQL 数据脚本命名须知**

**不可重复执行脚本**：
`V${version}_${time}__${name}.sql`，其中前缀V、后缀.sql、分隔符__ ；
**可重复执行脚本**：
`R__V${version}_${time}__${name}.sql`，其中前缀V、后缀.sql、分隔符__；


**注意**
脚本执行顺序

「 可重复执行脚本（R）」 的执行顺序在 「 不可重复执行脚本（V）」 之后（此处有坑）

执行顺序的重要性（依靠版本号来约束）：如果是进行数据脚本迁移且现有的数据库被清空的情况下（即空数据库情况下），那么建表语句需先执行（最小版本号）才行。如果建表语句写成可重复脚本的话，那么程序将会报错，因为「 可重复执行脚本（R）」 的执行顺序在 「 不可重复执行脚本（V）」 之后，所以此时数据表还没建立，对于数据表中的数据操作都将报错

至此 flyway 引入完成，启动项目，坐等执行成功即可~

