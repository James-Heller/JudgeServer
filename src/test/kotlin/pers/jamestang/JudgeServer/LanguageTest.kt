package pers.jamestang.JudgeServer

import org.junit.jupiter.api.Test
import pers.jamestang.judgeServer.runner.Runner

class LanguageTest {


    @Test
    fun clangTest(){
        val runner = Runner("./executable/add.out", "add","c_cpp").run(1000,2000, 128*1024*1024, null,200,10000, 32*1024*1024)

        println(runner)
    }

    @Test
    fun javaTest(){
        val runner = Runner("/usr/bin/java", "add", null).run(3000,5000, -1, listOf("-cp", "./executable", "add"), 200,10000, 32*1024*1024)

        println(runner)
    }
}