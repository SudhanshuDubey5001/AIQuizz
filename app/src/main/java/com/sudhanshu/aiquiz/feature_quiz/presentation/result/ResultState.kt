package com.sudhanshu.aiquiz.feature_quiz.presentation.result

import androidx.compose.ui.graphics.Color
import com.sudhanshu.aiquiz.core.utils.AppConfigurationConstants

data class ResultState(
    val correctAnswers: Int = 0,
    val totalQuestions: Int = 0,
    val scoreText: String = "",
    val scorePercentage: Float = 0f,
    val grade: Grade = Grade.VERY_BAD,
    val progressIndicatorColor: Color = AppConfigurationConstants.COLOR_VERY_BAD
)

enum class Grade{
    VERY_BAD, BAD, FINE, GOOD, VERY_GOOD, EXCELLENT
}
