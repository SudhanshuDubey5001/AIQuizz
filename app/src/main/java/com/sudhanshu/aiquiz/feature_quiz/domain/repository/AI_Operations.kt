package com.sudhanshu.aiquiz.feature_quiz.domain.repository

import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.AIModelData

interface AI_Operations {
    suspend fun gAI_generateAIResponse(prompt: String, aiModelData: AIModelData): String
}