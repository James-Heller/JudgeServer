package pers.jamestang.judgeServer.compiler

import pers.jamestang.judgeServer.config.Config
import pers.jamestang.judgeServer.entity.LanguageType
import java.io.File
import java.util.Random

class Compile(private val languageType: LanguageType, private val code: String, private val problemName: String) {

    private lateinit var currentCodeFile: File
    private lateinit var javaCodeFileDir: File
    private lateinit var javaBinaryDirPrefix: String
    private lateinit var binaryFilePath: String
    fun compile(): String? {

        this.codeToFile()
        val cmd = this.genCompileCmd()
        val builder = ProcessBuilder()
        builder.command(cmd)

        val process = builder.start()
        process.waitFor()
        currentCodeFile.delete()
        if (languageType == LanguageType.Java) javaCodeFileDir.delete()
        if (process.exitValue() == 0){

            binaryFilePath = when(languageType){
                LanguageType.C -> "${currentCodeFile.nameWithoutExtension}.out"
                LanguageType.Cpp -> "${currentCodeFile.nameWithoutExtension}.out"
                LanguageType.Go -> "${currentCodeFile.nameWithoutExtension}.gout"
                LanguageType.Java ->"${javaBinaryDirPrefix}/${currentCodeFile.nameWithoutExtension}.class"
                LanguageType.Python3 ->  "${currentCodeFile.nameWithoutExtension}.py"
                LanguageType.Python2 ->  "${currentCodeFile.nameWithoutExtension}.py"
            }

            return binaryFilePath
        }

        return null
    }

    /**
     * This method need to be called when judge is done not matter the result is success or fail.
     * Otherwise, the generated binary file will stay in server.
     */
    fun deleteBinaryFile(){

        File(binaryFilePath).delete()
    }

    private fun genCompileCmd(): List<String> {



        val cmd: List<String> = when(languageType){

            LanguageType.C -> "/usr/bin/gcc -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c99 ${currentCodeFile.path} -lm -o ${Config.executableBasePath}/${currentCodeFile.nameWithoutExtension}.out".split(' ')
            LanguageType.Cpp -> "/usr/bin/g++ -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c++11 ${currentCodeFile.path} -lm -o ${Config.executableBasePath}/${currentCodeFile.nameWithoutExtension}.out".split(' ')
            LanguageType.Java -> {
                javaBinaryDirPrefix = Random().nextInt().toString()
                "/usr/bin/javac -d ${Config.executableBasePath}/${javaBinaryDirPrefix} ${currentCodeFile.path}".split(' ')
            }
            LanguageType.Go -> "/usr/bin/go build -o ${Config.executableBasePath}/${currentCodeFile.nameWithoutExtension}.gout ${currentCodeFile.path}".split(' ')
            LanguageType.Python3 -> "mv ${currentCodeFile.path} ./executable".split(' ')
            LanguageType.Python2 -> "mv ${currentCodeFile.path} ./executable".split(' ')
        }

        return cmd
    }

    private fun codeToFile() {

        val baseName = Random().nextInt().toString() + problemName
        val filename: String = when(languageType){
            LanguageType.C -> "$baseName.c"
            LanguageType.Cpp -> "$baseName.cpp"
            LanguageType.Java -> "$baseName/$problemName.java"
            LanguageType.Go -> "$baseName.go"
            LanguageType.Python3 -> "$baseName.py"
            LanguageType.Python2 -> "$baseName.py"
        }

        currentCodeFile = if (languageType == LanguageType.Java){

            javaCodeFileDir = File("${Config.srcBasePath}/$baseName")
            javaCodeFileDir.mkdir()

            val file = File("${Config.srcBasePath}/$filename")
            file.createNewFile()
            file.writeText(code)
            file

        }else{
            val file = File("${Config.srcBasePath}/$filename")
            file.createNewFile()
            file.writeText(code)
            file
        }



    }

}