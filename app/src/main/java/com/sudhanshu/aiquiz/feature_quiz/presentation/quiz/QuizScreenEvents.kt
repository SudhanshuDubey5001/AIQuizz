package com.sudhanshu.aiquiz.feature_quiz.presentation.quiz

sealed class QuizScreenEvents{
    data class SendPageSelectedEvent(val page: Int) : QuizScreenEvents()

    object RetryLoadingMoreQuestions : QuizScreenEvents()

    data class OnOptionSelected(
        val questionIndex: Int,
        val optionIndex: Int,
        val questionVisitedStateChange: Boolean): QuizScreenEvents()
}
