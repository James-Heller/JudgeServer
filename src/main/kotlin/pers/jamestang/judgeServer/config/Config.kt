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
     * JDBC Config
     */
    const val jdbcURL = "jdbc:mysql://118.178.239.179:3366/OJCore"
    const val jdbcDriver = "com.mysql.cj.jdbc.Driver"
    const val username = "root"
    const val password = "252566"
    const val IdleTimeout = 60000L
    const val autoCommit = true
    const val maxPoolSize = 5
    const val minimumIdle = 1
    const val maxLifeTime = 600000L
    const val connectionTestQuery = "SELECT 1"
}