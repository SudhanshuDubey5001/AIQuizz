package com.sudhanshu.aiquiz.feature_quiz.presentation.loading

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sudhanshu.aiquiz.core.presentation.UiEvent
import com.sudhanshu.aiquiz.core.utils.Prompts
import com.sudhanshu.aiquiz.core.utils.Screens
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.QuizConfig
import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.QuizData
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Question
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Quiz
import com.sudhanshu.aiquiz.feature_quiz.domain.repository.AI_Operations
import com.sudhanshu.aiquiz.feature_quiz.domain.repository.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoadingScreenVM @Inject constructor(
    private val aiOperations: AI_Operations,
    private val quizConfig: QuizConfig,
    private val quizData: QuizData
) : ViewModel() {

    private val config = quizConfig.configuration.value
    private val topics = config.topics

    private val difficultyLevel = config.level
    private var topicsCombined = ""

    private var safety = true
    private var prompt = ""

    private val _retryCountSafety = mutableStateOf(0)   //to limit the retries to 3 API calls
    val retryCountSafety: State<Int> = _retryCountSafety

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val error = mutableStateOf(false)

    init {
        topics.forEach { topic ->
            topicsCombined += "$topic, "
        }
        prompt = Prompts.generateQuizPrompt(
            topics = topicsCombined,
            level = difficultyLevel
        )

        if (safety) {
            safety = false
            generateQuiz(prompt)
        }
    }

    fun onEvents(event: LoadingScreenEvents) {
        when (event) {
            LoadingScreenEvents.retryGeneratingQuiz -> {
                _retryCountSafety.value++
                generateQuiz(prompt)
                error.value = false
            }
        }
    }

    private fun mockTest(){ // TODO: Remove this method
        viewModelScope.launch {
            delay(2500)
            _retryCountSafety.value++
            error.value = true
        }
    }

    private fun generateQuiz(prompt: String) {
        viewModelScope.launch {
            try {
                Utils.log("Calling API first time")
                val response = aiOperations.gAI_generateAIResponse(prompt)
//                Utils.log("Raw format == $response")
                val json = Utils.extractJson(response)
//                Utils.log("Corrected format == $json")
                val quiz = Gson().fromJson(json, Quiz::class.java)
                Utils.log("Quiz = $quiz")
                val uniqueQuestionsSet = removeDuplicateQuestions(quiz.questions)
                quizData.setQuizData(Quiz(uniqueQuestionsSet))
                _uiEvent.emit(UiEvent.navigate(Screens.QUIZ))
            } catch (e: Exception) {
                error.value = true
                Utils.log(e.toString())
            }
        }
    }

    private fun removeDuplicateQuestions(questions: List<Question>): List<Question>{
        val uniqueQuestion = HashSet<String>()
        return questions.filter { question: Question ->
            return@filter uniqueQuestion.add(question.question)
        }
    }
}