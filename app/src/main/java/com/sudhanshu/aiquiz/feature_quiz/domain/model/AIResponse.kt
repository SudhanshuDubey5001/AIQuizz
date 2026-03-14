package com.sudhanshu.aiquiz.feature_quiz.domain.model

data class AIResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

data class Message(
    val role: String,
    val content: String
)
