package com.sudhanshu.aiquiz.feature_quiz.domain.model

data class Configuration(
    val topics: List<String>,
    val level: Level = Level.EASY,
    val questionsCount: Int = 10,
)

enum class Level{
    EASY, MEDIUM, HARD
}