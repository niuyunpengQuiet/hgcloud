# 依赖服务模块

####Actuator监控应用启动组件: hgcloud-actuator-app

####Gateway网关应用启动组件: hgcloud-gateway-app

####Nacos注册中心应用启动组件: hgcloud-nacos-app

####Sentinel流量保护应用启动组件: hgcloud-sentinel-app

####XxlJob分布式任务调度应用启动组件: hgcloud-xxl-job-app

#### TIP
##### maven配置技巧：
###### 方式1:
````
<mirror>
    <id>mirrorId</id>
    <mirrorOf>repositoryId</mirrorOf>
    <name>Human Readable Name for this Mirror.</name>
    <url>http://mvnrepository.com/</url>
</mirror>
````
###### 方式2:
````
<mirror>
    <id>aliyun-public</id>
    <mirrorOf>*,!spring-milestones</mirrorOf>
    <url>https://maven.aliyun.com/repository/public</url>
</mirror>
````