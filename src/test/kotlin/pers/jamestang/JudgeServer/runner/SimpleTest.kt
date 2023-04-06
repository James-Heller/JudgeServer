package pers.jamestang.JudgeServer.runner

import org.junit.jupiter.api.Test
import java.io.File

class SimpleTest {


    @Test
    fun filenameTest(){

        val file = File("libjudger.so")

        println(file.path)
        println(file.absoluteFile)
    }
}