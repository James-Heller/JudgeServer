package pers.jamestang.judgeServer.config

object Config {

    /**
     * Dir location
     */
    const val executeResultBasePath = "./executeOut"
    const val testCaseBasePath = "./testcase"
    const val logBasePath = "./log"
    const val srcBasePath = "./code"
    const val executableBasePath = "./executable"


    /**
     *  Mysql Config
     */
    const val mysqlConnectURL = "jdbc:mysql://118.178.239.179:3366/OJCore"
    const val mysqlUsername = "root"
    const val mysqlPassword = "252566"

    /**
     * RabbitMQ Config
     */
    const val rabbitMQConnectURL = "118.178.239.179"
    const val rabbitMQConnectPort = 5672
    const val rabbitMQConnectUsername = "JamesTang"
    const val rabbitMQConnectPassword = "252566"
    const val requestQueueName = "user_source"
    // This value is control how many task can do in the same time
    const val prefetchCount = 1
}