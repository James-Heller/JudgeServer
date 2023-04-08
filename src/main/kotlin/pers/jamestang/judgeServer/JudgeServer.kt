package pers.jamestang.judgeServer

import com.alibaba.fastjson2.JSON
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import org.ktorm.database.Database
import org.ktorm.dsl.insert
import pers.jamestang.judgeServer.annotation.Slf4j
import pers.jamestang.judgeServer.annotation.Slf4j.Companion.log
import pers.jamestang.judgeServer.config.Config
import pers.jamestang.judgeServer.dao.ResultDB
import pers.jamestang.judgeServer.entity.Source
import pers.jamestang.judgeServer.judge.Judges
import java.io.File
import java.nio.charset.Charset

@Slf4j
class JudgeServer{

    fun start(){

        initDir()

        val factory = ConnectionFactory()
        factory.host = Config.rabbitMQConnectURL
        factory.port = Config.rabbitMQConnectPort
        factory.username = Config.rabbitMQConnectUsername
        factory.password = Config.rabbitMQConnectPassword
        val connection = factory.newConnection()
        val channel = connection.createChannel()

        channel.queueDeclare(Config.requestQueueName, false, false, false, null)
        channel.basicQos(Config.prefetchCount)
        log.info("Server Started")
        val deliverCallback = DeliverCallback{ _, delivery ->
            run {

                val bytes = delivery.body

                val jsonString = String(bytes, Charset.forName("UTF-8"))
                val source = JSON.parseObject(jsonString, Source::class.java)

                log.info("Get code for ${source.problemName} from ${source.userId}, Begin judge.")
                doJudge(source)
            }
        }
        channel.basicConsume(Config.requestQueueName, true, deliverCallback, CancelCallback {  })



    }

    private fun initDir(){

        checkDirExist(Config.srcBasePath)
        checkDirExist(Config.executableBasePath)
        checkDirExist(Config.executeResultBasePath)
        checkDirExist(Config.logBasePath)
    }

    private fun checkDirExist(path: String){

        val dir = File(path)

        if (dir.isDirectory){
            return
        }

        dir.mkdir()
    }

    private fun doJudge(source: Source){

        val judges = Judges(source.languageType, source.code, source.problemName)
        val resultList = judges.judge()
        var avgCpuTime = 0
        var avgRealTime = 0
        var avgMemUse = 0
        var finalResult = true
        for (result in resultList){
            if(!result.md5Check){
                avgCpuTime =0
                avgRealTime = 0
                avgMemUse = 0
                finalResult = false
                break
            }

            avgCpuTime += result.cpuTime
            avgRealTime += result.realTime
            avgMemUse += result.memory!!
        }
        //Get avg data
        avgCpuTime /= resultList.size
        avgRealTime /= resultList.size
        avgMemUse /= resultList.size


        saveToDB(source.userId, avgCpuTime, avgRealTime, avgMemUse, finalResult)
    }

    private fun saveToDB(userId: Int, avgCpuTime: Int, avgRealTime: Int, avgMemUse: Int, finalResult: Boolean){

        val db = Database.connect(url = Config.mysqlConnectURL, user = Config.mysqlUsername, password = Config.mysqlPassword)

        db.insert(ResultDB){
            set(it.userId, userId)
            set(it.cpuTime, avgCpuTime)
            set(it.realTime, avgRealTime)
            set(it.memory, avgMemUse)
            set(it.md5Check, finalResult)
        }
    }
}
fun main() {

    JudgeServer().start()
}

