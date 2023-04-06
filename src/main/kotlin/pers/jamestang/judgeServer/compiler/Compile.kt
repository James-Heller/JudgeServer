package pers.jamestang.judgeServer.compiler

import pers.jamestang.judgeServer.config.Config
import pers.jamestang.judgeServer.entity.LanguageType
import java.io.File
import java.util.Random

class Compile(private val languageType: LanguageType, private val code: String, private val problemName: String) {

    private lateinit var currentCodeFile: File
    fun compile(): String? {

        this.codeToFile()
        val cmd = this.genCompileCmd()
        val builder = ProcessBuilder()
        println(cmd)
        builder.command(cmd)

        val process = builder.start()
        process.waitFor()
        if (process.exitValue() == 0){

            return when(languageType){
                LanguageType.C -> "${currentCodeFile.nameWithoutExtension}.out"
                LanguageType.Cpp -> "${currentCodeFile.nameWithoutExtension}.out"
                LanguageType.Go -> "${currentCodeFile.nameWithoutExtension}.gout"
                LanguageType.Java ->"${currentCodeFile.nameWithoutExtension}.class"
                LanguageType.Python3 ->  "${currentCodeFile.nameWithoutExtension}.py"
                LanguageType.Python2 ->  "${currentCodeFile.nameWithoutExtension}.py"
            }
        }

        return null
    }

    private fun genCompileCmd(): List<String> {



        val cmd: List<String> = when(languageType){

            LanguageType.C -> "/usr/bin/gcc -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c99 ${currentCodeFile.path} -lm -o ${Config.executableBasePath}/${currentCodeFile.nameWithoutExtension}.out".split(' ')
            LanguageType.Cpp -> "/usr/bin/g++ -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c++11 ${currentCodeFile.path} -lm -o ${Config.executableBasePath}/${currentCodeFile.nameWithoutExtension}.out".split(' ')
            LanguageType.Java -> "/usr/bin/javac -d ${Config.executableBasePath} ${currentCodeFile.path}".split(' ')
            LanguageType.Go -> "/usr/bin/go build -o ${Config.executableBasePath}/${currentCodeFile.nameWithoutExtension}.gout ${currentCodeFile.path}".split(' ')
            LanguageType.Python3 -> "mv ${currentCodeFile.path} ./executable".split(' ')
            LanguageType.Python2 -> "mv ${currentCodeFile.path} ./executable".split(' ')
        }

        return cmd
    }

    private fun codeToFile() {

        val baseName = Random().nextInt(10).toString() + problemName
        val filename: String = when(languageType){
            LanguageType.C -> "$baseName.c"
            LanguageType.Cpp -> "$baseName.cpp"
            LanguageType.Java -> "$problemName.java"
            LanguageType.Go -> "$baseName.go"
            LanguageType.Python3 -> "$baseName.py"
            LanguageType.Python2 -> "$baseName.py"
        }

        val file = File("${Config.srcBasePath}/$filename")
        file.createNewFile()
        file.writeText(code)

        currentCodeFile = file

    }

}