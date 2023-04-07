package pers.jamestang.judgeServer.judge

import pers.jamestang.judgeServer.compiler.Compile
import pers.jamestang.judgeServer.config.Config
import pers.jamestang.judgeServer.entity.LanguageType
import pers.jamestang.judgeServer.entity.Result
import pers.jamestang.judgeServer.runner.Runner
import java.io.File

class Judges(private val languageType: LanguageType, private val code: String, private val problemName: String) {


    private lateinit var compiler: Compile


    companion object{
        /**
         * This function need to be called when all works done.
         * Otherwise, binary file will stay in computer
         */
        fun clearBinaryDir(){
            deleteFiles(File("./executable"))
        }

        private fun deleteFiles(dir: File){

            val files = dir.listFiles()!!
            for(file in files){
                if (file.isDirectory){
                    deleteFiles(file)
                }else{
                    file.delete()
                }
            }

            if (dir.nameWithoutExtension != "executable") dir.delete()
        }
    }
  fun judge(): List<Result>{

      val binaryFilePath = compile()

      val result = when(languageType){

          LanguageType.C, LanguageType.Cpp -> {
              Runner("${Config.executableBasePath}/$binaryFilePath", problemName, "c_cpp").getResult(
                  -1,-1, -1, null, null, 200,10000, 32*1024*1024
              )
          }

          LanguageType.Go -> {
              Runner("${Config.executableBasePath}/$binaryFilePath", problemName, null).getResult(2000, 4000, -1, null, listOf("GODEBUG=madvdontneed=1", "GOCACHE=off"), 200, 10000, 32*1024*1024)
          }

          LanguageType.Java ->{
              Runner("/usr/bin/java", problemName, null).getResult(
                  3000,5000, -1, listOf("-cp", "${Config.executableBasePath}/${compiler.javaBinaryDirPrefix}", File(binaryFilePath).nameWithoutExtension), null, 200,10000, 32*1024*1024)
          }

          LanguageType.Python3 -> {
              Runner("/usr/bin/python3", problemName, "general").getResult(
                  3000,5000, -1, listOf("${Config.executableBasePath}/$binaryFilePath"), null, 200, 10000, 32*1024*1024)
          }

          LanguageType.Python2 -> {
              Runner("/usr/bin/python2", problemName, "general").getResult(
                  3000,5000, -1, listOf("${Config.executableBasePath}/$binaryFilePath"), null, 200, 10000, 32*1024*1024)
          }
      }


      return result
  }



    private fun compile(): String{
        compiler = Compile(languageType, code, problemName)

        val binaryPath = compiler.compile()

        if (binaryPath != null){
            return binaryPath
        }

        throw RuntimeException("Compile Error")
    }


}