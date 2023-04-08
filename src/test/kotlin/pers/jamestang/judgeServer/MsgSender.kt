package pers.jamestang.judgeServer

import com.alibaba.fastjson2.toJSONByteArray
import com.rabbitmq.client.ConnectionFactory
import pers.jamestang.judgeServer.entity.LanguageType
import pers.jamestang.judgeServer.entity.Source

fun main(){

    val factory = ConnectionFactory()
    factory.host = "118.178.239.179"
    factory.port = 5672
    factory.username = "JamesTang"
    factory.password = "252566"
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.queueDeclare("user_source", false, false, false, null)
    val data = Source(
        1,
        """
                import java.util.Scanner;
                public class add{
                    public static void main(String args[]){
                        Scanner scanner = new Scanner(System.in);
                        int a,b ;
                        a = scanner.nextInt();
                        b = scanner.nextInt();
                        System.out.print(a+b);
                        
                    }
                    
                }
            """.trimIndent(),
        "add",
        LanguageType.Java
    )

    channel.basicPublish("", "user_source", null, data.toJSONByteArray())

    channel.close()
}