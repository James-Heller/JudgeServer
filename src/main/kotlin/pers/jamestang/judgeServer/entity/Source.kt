package pers.jamestang.judgeServer.entity

data class Source(
    val languageType: LanguageType,
    val code: String,
    val userId: Int
)
