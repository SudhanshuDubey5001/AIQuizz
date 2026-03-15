package com.sudhanshu.aiquiz.core.utils

import androidx.compose.ui.graphics.Color

object AppConfigurationConstants {
    const val ENTRY_SCREEN = Screens.WELCOME
    // number of times user is allowed to press retry
    const val GENERATIVE_AI_API_CALL_RETRY_LIMIT = 3
    const val QUESTIONS_COUNT_PER_API_CALL = 10  // IMPORTANT: Changing this value might affect the JSON response!!
    const val DEBOUNCE_TIME_MS = 2000L  //debounce for calling Gemini in text input
    const val AUTOMATIC_GENERATE_QUESTIONS_OFFSET = 4
    const val AI_MODEL = "meta-llama/llama-4-scout-17b-16e-instruct"

    const val RETRY_GENERATE_MORE_QUESTION_CT = 3

    //result indicator colors
    val COLOR_VERY_BAD = Color(0xFF7A0000)
    val COLOR_BAD = Color(0xFFD31E1E)
    val COLOR_FINE = Color(0xFFFFB300)
    val COLOR_GOOD = Color(0xFFC0CA33)
    val COLOR_VERY_GOOD = Color(0xFF7CB342)
    val COLOR_EXCELLENT = Color(0xFF20C514)

    //result messages
    const val RESULT_MSG_VERY_BAD = "Ew.."
    const val RESULT_MSG_BAD = "Not bad..or is it?"
    const val RESULT_MSG_FINE = "Getting there.."
    const val RESULT_MSG_GOOD = "Good job!"
    const val RESULT_MSG_VERY_GOOD = "Impressive!"
    const val RESULT_MSG_EXCELLENT = "Outstanding!"
    const val RESULT_MSG_PERFECT_SCORE = "Perfect Score!!"

    //Pie charts colors
    val PIE_CHART_CORRECT_COLOR = Color(0xFF2E8B57)
    val PIE_CHART_INCORRECT_COLOR = Color(0xFFBA1A1A)
}