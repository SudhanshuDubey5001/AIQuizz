package com.sudhanshu.aiquiz.feature_quiz.presentation.quiz

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.sudhanshu.aiquiz.core.presentation.UiEvent
import com.sudhanshu.aiquiz.core.utils.AppConfigurationConstants
import com.sudhanshu.aiquiz.core.utils.ErrorMessages
import com.sudhanshu.aiquiz.core.utils.Prompts
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Question
import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.QuizConfig
import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.QuizData
import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.UserData
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Quiz
import com.sudhanshu.aiquiz.feature_quiz.domain.repository.AI_Operations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizScreenVM @Inject constructor(
    private val aiOperations: AI_Operations,
    private val quizConfigInstance: QuizConfig,
    private val userDataInstance: UserData,
    private val quizDataInstance: QuizData
) : ViewModel() {

    val quizData = quizDataInstance.quizQuestions
    val userData = userDataInstance.user

    val quizConfig = quizConfigInstance.configuration.value

    private val _loadingMoreQuestions = mutableStateOf(LoadingState.IDLE)
    val loadingMoreQuestions = _loadingMoreQuestions

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var generateQuestionsJob: Job? = null

    //increment the index as user goes forward in question series
//    private var triggerGenerateMoreQuestionIndex = mutableStateOf(1)

    //keeping track of api call retries
    private var retryCount = mutableStateOf(0)

    private fun generateMoreQuestions() {
        _loadingMoreQuestions.value = LoadingState.LOADING
        generateQuestionsJob = viewModelScope.launch {
            try {
                val moreQuestions =
                    getMoreQuizQuestions(mutableListOf(), quizDataInstance.quizQuestions)
                Utils.log("More questions = $moreQuestions")
                quizDataInstance.setQuizData(moreQuestions)
                resetRetryCount()
                _loadingMoreQuestions.value = LoadingState.IDLE
            } catch (e: Exception) {
                if (retryCount.value <= AppConfigurationConstants.GENERATIVE_AI_API_CALL_RETRY_LIMIT)
                    _loadingMoreQuestions.value = LoadingState.ERROR
                else _loadingMoreQuestions.value = LoadingState.STOP_RETRY

                _uiEvent.emit(UiEvent.showSnackBar(ErrorMessages.FAILED_MORE_QUIZ_QUESTIONS_GENERATE))
                Utils.log(e.toString())
            }
        }
    }

    fun onEvents(event: QuizScreenEvents) {
        when (event) {
            is QuizScreenEvents.SendPageSelectedEvent -> {
                generateQuestionsJob?.let { if (it.isActive) return }
                if ((event.page + AppConfigurationConstants.AUTOMATIC_GENERATE_QUESTIONS_OFFSET) >= quizData.size) {
                    generateMoreQuestions()
                }
            }

            QuizScreenEvents.RetryLoadingMoreQuestions -> {
                generateMoreQuestions()
                incrementRetryCount()
            }

            is QuizScreenEvents.OnOptionSelected -> {
                quizDataInstance.setOptionSelected(
                    questionIndex = event.questionIndex,
                    optionSelectedIndex = event.optionIndex,
                    visitedStatus = event.questionVisitedStateChange
                )
                userDataInstance.addUserAnswer(
                    questionIndex = event.questionIndex,
                    value = quizData[event.questionIndex].options[event.optionIndex]
                )
            }
        }
    }

    private fun cancelGenerateMoreQuestionsAPICall() {
        generateQuestionsJob?.cancel()
    }

    private fun resetRetryCount() {
        retryCount.value = 0
    }

    private fun incrementRetryCount() {
        retryCount.value++
    }

    private suspend fun getMoreQuizQuestions(
        duplicateQuestionList: MutableList<String>,
        loadedQuestions: SnapshotStateList<Question>
    ): Quiz {
        val topics = quizConfig.topics.joinToString(separator = ", ")
        val level = quizConfig.level
        val prompt = Prompts.generateQuizPromptForNextQuestions(
            topics = topics,
            level = level,
            duplicateQuestionList = duplicateQuestionList
        )
        Utils.log("Calling API ======> ${retryCount.value.toString()}")
        val response = aiOperations.gAI_generateAIResponse(prompt)
//        Utils.log(response)
//        Utils.log("Raw format == $response")
        val json = Utils.extractJson2(response)
//        Utils.log("Pretty format == $json")
//        val mapper = ObjectMapper()
//        val jsonNode = mapper.readTree(json)
//        Utils.log("JSON NODE = " + json.toString())
        val quizList = Gson().fromJson(json.toString(), Quiz::class.java)
//        return quizList

        for (oldQuestion in loadedQuestions) {
            for (question in quizList.questions) {
//                Utils.log("LoadedQuestion : ${oldQuestion.question}")
//                Utils.log("NewQuestion : ${question.question}")
                val score = Utils.similarityScore(
                    question.question,
                    oldQuestion.question
                )
//                Utils.log("Score = ${score}")
//                Utils.log("Retry counter = ${retryCount.value}")
                if (score >= 0.85) {  //if similarity score is more than 0.85 then its quite similar
                    if (retryCount.value < AppConfigurationConstants.GENERATIVE_AI_API_CALL_RETRY_LIMIT) {
                        Utils.log("Questions similar, fetching new questions")
                        duplicateQuestionList.add(question.question)
                        Utils.log("Duplicate Questions = ${duplicateQuestionList.toString()}")
                        incrementRetryCount()
                        getMoreQuizQuestions(duplicateQuestionList, loadedQuestions)
                        break
                    } else {
                        resetRetryCount()
                        return quizList
                    }
                }
            }
        }
        return quizList
    }
}