package com.sudhanshu.aiquiz.feature_quiz.presentation.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Question
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Quiz
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Resource
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Resources
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizData @Inject constructor() {

    private val _quizQuestions = mutableStateListOf<Question>()
    val quizQuestions: SnapshotStateList<Question> = _quizQuestions

    private val _resources = mutableStateListOf<Resource>()
    val resources : SnapshotStateList<Resource> = _resources

    // --------------Mock test--------
//    val mockQuiz = Gson().fromJson(MockResponses.response1, Quiz::class.java)
//    init {
//        _quizQuestions.addAll(mockQuiz.questions)
//    }
// ---------------------------------------

    fun setQuizData(data: Quiz) {
        _quizQuestions.addAll(data.questions)
    }

    fun setOptionSelected(
        questionIndex: Int,
        optionSelectedIndex: Int,
        visitedStatus: Boolean
    ) {
        _quizQuestions[questionIndex] = _quizQuestions[questionIndex].copy(
            optionSelected = optionSelectedIndex,
            questionAttempted = visitedStatus,
            isCorrect = isCorrect(questionIndex,optionSelectedIndex)
        )
    }

    fun setResources(data: Resources){
        _resources.addAll(data.resources)
    }

    fun getTotalCorrectAnswer(): Int{
        var totalCorrect = 0
        _quizQuestions.map{question ->
            if(question.isCorrect) totalCorrect++
        }
        return totalCorrect
    }

    fun resetData(){
        _quizQuestions.clear()
        _resources.clear()
    }

    private fun isCorrect(questionIndex: Int, optionSelectedIndex: Int): Boolean{
        val correct_answer = if(quizQuestions[questionIndex].correct_answer.toString()!="") quizQuestions[questionIndex].correct_answer.toString() else quizQuestions[questionIndex].correct.toString()
        Utils.log("Correct answer = ${correct_answer} and selected questionIndex = ${optionSelectedIndex}")
        val userIndexChar = when (correct_answer) {
            "A" -> 0
            "B" -> 1
            "C" -> 2
            "D" -> 3
            else -> -1
        }
        val userIndexCharInt = when (correct_answer) {
            "0" -> 0
            "1" -> 1
            "2" -> 2
            "3" -> 3
            else -> -1
        }
        val userIndexCharInt2 = when (correct_answer) {
            "0.0" -> 0
            "1.0" -> 1
            "2.0" -> 2
            "3.0" -> 3
            else -> -1
        }
        val userAnswer = quizQuestions[questionIndex].options[optionSelectedIndex]
        return userAnswer == correct_answer || userIndexChar == optionSelectedIndex || userIndexCharInt == optionSelectedIndex || userIndexCharInt2 == optionSelectedIndex
    }
}