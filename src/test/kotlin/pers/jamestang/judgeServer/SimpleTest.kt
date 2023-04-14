package pers.jamestang.judgeServer

import com.rabbitmq.client.ConnectionFactory
import org.junit.jupiter.api.Test
import pers.jamestang.judgeServer.annotation.Slf4j
import pers.jamestang.judgeServer.annotation.Slf4j.Companion.log
import java.io.File

@Slf4j
class SimpleTest {


    @Test
    fun filenameTest(){

        val file = File("./executable/libjudger.so")


        println(file.path)
        println(file.absoluteFile)
        println(file.canonicalPath)
        println(file.nameWithoutExtension)
    }



    @Test
    fun simpleServer(){
        val factory = ConnectionFactory()
        factory.host = "118.178.239.179"
        factory.port = 5672
        factory.username = "JamesTang"
        factory.password = "252566"
        val connection = factory.newConnection()
        val channel = connection.createChannel()

        channel.basicPublish("", "key", null, "HAHA".toByteArray())

        log.info("Server Started")
    }
}