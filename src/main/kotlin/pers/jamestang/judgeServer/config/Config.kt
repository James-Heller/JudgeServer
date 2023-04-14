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
    const val mysqlConnectURL = "jdbc:mysql://"
    const val mysqlUsername = ""
    const val mysqlPassword = ""

    /**
     * RabbitMQ Config
     */
    const val rabbitMQConnectURL = ""
    const val rabbitMQConnectPort = 5672
    const val rabbitMQConnectUsername = ""
    const val rabbitMQConnectPassword = ""
    const val requestQueueName = "user_source"
    // This value is control how many task can do in the same time
    const val prefetchCount = 1
}