package pers.jamestang.judgeServer

import pers.jamestang.judgeServer.config.Config
import java.io.File


fun main() {

    checkDirExist(Config.srcBasePath)
    checkDirExist(Config.executableBasePath)
    checkDirExist(Config.executeResultBasePath)
    checkDirExist(Config.logBasePath)
}

private fun checkDirExist(path: String){

    val dir = File(path)

    if (dir.isDirectory){
        return
    }

    dir.mkdir()
}