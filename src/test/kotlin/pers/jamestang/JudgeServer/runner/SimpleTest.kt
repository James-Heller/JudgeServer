package pers.jamestang.JudgeServer.runner

import org.junit.jupiter.api.Test
import java.io.File

class SimpleTest {


    @Test
    fun filenameTest(){

        val file = File("./executable/libjudger.so")


        println(file.path)
        println(file.absoluteFile)
        println(file.canonicalPath)
        println(file.nameWithoutExtension)
    }
}