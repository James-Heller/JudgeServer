package pers.jamestang.judgeServer.entity

data class Result (
    val cpuTime: Int,
    val realTime: Int,
    val memory: Int?,
    val md5Check:Boolean

)