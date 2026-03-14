package com.sudhanshu.aiquiz.feature_quiz.presentation.result

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sudhanshu.aiquiz.core.utils.AppConfigurationConstants
import com.sudhanshu.aiquiz.core.utils.Prompts
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.QuizConfig
import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.QuizData
import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.UserData
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Question
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Resources
import com.sudhanshu.aiquiz.feature_quiz.domain.repository.AI_Operations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ResultScreenVM @Inject constructor(
    private val aiOperations: AI_Operations,
    private val quizDataInstance: QuizData,
    private val userDataInstance: UserData,
    private val quizConfig: QuizConfig
) : ViewModel(){

    private val config = quizConfig.configuration.value
    val topics = config.topics

    private var topicsCombined = ""
    private var prompt = ""
    private var safety = true

    val questions = quizDataInstance.quizQuestions
    val userData = userDataInstance.user
    val resources = quizDataInstance.resources
    val resultState = mutableStateOf(ResultState())

    private val _loadingResource = mutableStateOf(false)
    val loadingResources = _loadingResource

    init {
        resultState.value = calculateScore()
        topics.forEach { topic ->
            topicsCombined += "$topic, "
        }
        prompt = Prompts.generateResources(topics = topicsCombined)
//        prompt = Prompts.generateResources(topics = "Science, Animals, Medical science")
        if(safety){
            safety = false
            generateResources(prompt)
        }
    }

    fun onEvents(event: ResultScreenEvents){
        when(event){
            ResultScreenEvents.ResetQuizData -> {
                quizDataInstance.resetData()
                quizConfig.resetData()
            }
        }
    }

    private fun calculateScore(): ResultState {
//        val totalCorrect = 17f
//        val totalNumberOfQuestions = 20f

        val totalCorrect : Float = quizDataInstance.getTotalCorrectAnswer().toFloat()
        Utils.log("Total correct = $totalCorrect")
        val totalNumberOfQuestions = quizDataInstance.quizQuestions
            .filter { question: Question -> question.questionAttempted }.size.toFloat()
        Utils.log("Total questions = $totalNumberOfQuestions")
        val percentage : Float = (totalCorrect/totalNumberOfQuestions)*100
        Utils.log("Percentage = $percentage")

        var grade: Grade = Grade.VERY_BAD
        var color: Color = AppConfigurationConstants.COLOR_VERY_BAD

        when {
            percentage>=0 && percentage<16.67 -> {
                grade = Grade.VERY_BAD
                color = AppConfigurationConstants.COLOR_VERY_BAD
            }
            percentage>=16.67 && percentage<33.34 -> {
                grade = Grade.BAD
                color = AppConfigurationConstants.COLOR_BAD
            }
            percentage>=33.34 && percentage<50 -> {
                grade = Grade.FINE
                color = AppConfigurationConstants.COLOR_FINE
            }
            percentage>=50 && percentage<66.67 -> {
                grade = Grade.GOOD
                color = AppConfigurationConstants.COLOR_GOOD
            }
            percentage>=66.67 && percentage<83.34 -> {
                grade = Grade.VERY_GOOD
                color = AppConfigurationConstants.COLOR_VERY_GOOD
            }
            percentage>=83.34 -> {
                grade = Grade.EXCELLENT
                color = AppConfigurationConstants.COLOR_EXCELLENT
            }
        }
        return ResultState(
            correctAnswers = totalCorrect.toInt(),
            totalQuestions = totalNumberOfQuestions.toInt(),
            scoreText = "${totalCorrect.toInt()}/${totalNumberOfQuestions.toInt()}",
            scorePercentage = percentage / 100,
            grade = grade,
            progressIndicatorColor = color
        )
    }

    private fun generateResources(prompt: String) {
        _loadingResource.value = true
        viewModelScope.launch {
            try {
                val response = aiOperations.gAI_generateAIResponse(prompt)
//                val response = MockResponses.resources  //mock resource
                Utils.log("Raw format == $response")
                val json = Utils.extractJson(response)
                val resources = Gson().fromJson(json, Resources::class.java)
                Utils.log("Resources = $resources")
                quizDataInstance.setResources(resources)
                _loadingResource.value = false
            } catch (e: Exception) {
                Utils.log(e.toString())
            }
        }
    }
}