package pers.jamestang.judgeServer.entity

import java.io.Serializable

data class Source(
    val userId: Int,
    val code: String,
    val problemName: String,
    val languageType: LanguageType
): Serializable
