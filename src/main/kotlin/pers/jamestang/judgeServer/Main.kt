package pers.jamestang.judgeServer

import pers.jamestang.judgeServer.config.DataSource


fun main() {

    val db = DataSource()
    db.getSession()
}



