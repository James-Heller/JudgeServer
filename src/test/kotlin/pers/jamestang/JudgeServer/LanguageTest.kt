package pers.jamestang.JudgeServer

import org.junit.jupiter.api.Test
import pers.jamestang.judgeServer.runner.Runner

class LanguageTest {


    @Test
    fun clangTest(){
        val runner = Runner("./test.out", "add","c_cpp").run(1000,2000, 128*1024*1024, 200,10000, 32*1024*1024)

        println(runner)
    }
}