# JudgeServer

#### 介绍
一个基于青岛大学OJ判题核心开发的JudgeServer

#### 软件架构
基于Kotlin开发，以Ktorm作为ORM框架，RabbitMQ作为消息队列支撑。
依赖于：RabbitMQ,Ktorm,MysqlConnector, Alibaba Fastjson2-kotlin。

**Java版本为Java17，Kotlin版本为1.8**

#### 安装教程

1.  clone仓库，并且修改config包下Config.kt里面的配置属性。
2.  编译为Jar


#### 使用说明

***
**你必须自行clone 来自青岛大学在Github上的Judger项目进行编译以获得*libjudger.so*文件。**
**在编译完成后你需要替换掉本项目内的*libjudger.so***

**打包成jar时，代码中的./所指的是jar的所在目录，在IDE中时为当前项目的跟目录**

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

