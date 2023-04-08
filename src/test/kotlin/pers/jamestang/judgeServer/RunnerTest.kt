package pers.jamestang.judgeServer.runner

import org.junit.jupiter.api.Test

class RunnerTest {


    @Test
    fun clangTest(){
        val result = Runner("./executable/5add.out", "add","c_cpp").getResult(1000,2000, 128*1024*1024, null, null, 200,10000, 32*1024*1024)

        println(result)
    }

    @Test
    fun javaTest(){
        val runner = Runner("/usr/bin/java", "add", null).run(3000,5000, -1, listOf("-cp", "./executable", "add"), null, 200,10000, 32*1024*1024)

        println(runner)
    }

    @Test
    fun python3Test(){
        val runner = Runner("/usr/bin/python3", "add", "general").run(3000,5000, -1, listOf("./executable/9add.py"), null, 200, 10000, 32*1024*1024)

        println(runner)
    }

    @Test
    fun goTest(){
        val runner = Runner("./executable/1add.gout", "add", null).run(1000, 2000, -1, null, listOf("GODEBUG=madvdontneed=1", "GOCACHE=off"), 200, 10000, 32*1024*1024)

        println(runner)
    }
}