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
在编译完成后你需要替换掉本项目内的*libjudger.so*

打包成jar时，代码中的./所指的是jar的所在目录，在IDE中时为当前项目的根目录。

运行此代码你只需要将消息发送端和你在配置类里面写的队列名称设为一致。

***你需要在发送端把包含本项目entity/Source类的对象转化为JSONByteArray进行发送***
#

#### 关于测试用例
***
测试用例都储存在testcase文件夹中，以子文件夹的形式存在。
每个题目都是一个子文件夹，测试用例和info.json存在于其中,
info.json的内容例子如下：
```json

{
  "testcases":[
    {
      "in":"1.in",
      "out":"1.out"
    },
    {
      "in": "2.in",
      "out": "2.out"
    }
  ]
}
```
info由一个JSON串构成，其中数组``testcases``是必要的，内部是由 ``in``, ``out``组成的obj元素。
``in``, ``out``必须成对出现，目前不能单独出现``in``或者是``out``
#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

