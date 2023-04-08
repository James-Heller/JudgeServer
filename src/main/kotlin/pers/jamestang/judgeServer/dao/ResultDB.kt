package pers.jamestang.judgeServer.dao

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int

object ResultDB : Table<Nothing>("result"){
    val id = int("id").primaryKey()
    val userId = int("user_id")
    val cpuTime = int("cpu_time")
    val realTime = int("real_time")
    val memory = int("memory")
    val md5Check = boolean("md5_check")
}