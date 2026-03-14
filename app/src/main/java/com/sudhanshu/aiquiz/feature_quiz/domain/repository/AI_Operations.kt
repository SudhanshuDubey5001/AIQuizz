package com.sudhanshu.aiquiz.feature_quiz.domain.repository

interface AI_Operations {
    suspend fun gAI_generateAIResponse(prompt: String): String
}