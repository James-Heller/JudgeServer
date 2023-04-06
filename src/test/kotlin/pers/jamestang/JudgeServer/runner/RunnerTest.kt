package pers.jamestang.JudgeServer.runner

import org.junit.jupiter.api.Test
import pers.jamestang.judgeServer.runner.Runner

class RunnerTest {


    @Test
    fun clangTest(){
        val runner = Runner("./executable/add.out", "add","c_cpp").run(1000,2000, 128*1024*1024, null, null, 200,10000, 32*1024*1024)

        println(runner)
    }

    @Test
    fun javaTest(){
        val runner = Runner("/usr/bin/java", "add", null).run(3000,5000, -1, listOf("-cp", "./executable", "add"), null, 200,10000, 32*1024*1024)

        println(runner)
    }

    @Test
    fun python3Test(){
        val runner = Runner("/usr/bin/python3", "add", "general").run(3000,5000, -1, listOf("./executable/add.py"), null, 200, 10000, 32*1024*1024)

        println(runner)
    }

    @Test
    fun python2Test(){
        val runner = Runner("/usr/bin/python2", "add", "general").run(3000, 5000, -1, listOf("./executable/add.py"), null, 200, 10000, 32*1024*1024)

        println(runner)
    }

    @Test
    fun goTest(){
        val runner = Runner("./executable/add.gout", "add", null).run(1000, 2000, -1, null, listOf("GODEBUG=madvdontneed=1", "GOCACHE=off"), 200, 10000, 32*1024*1024)

        println(runner)
    }
}