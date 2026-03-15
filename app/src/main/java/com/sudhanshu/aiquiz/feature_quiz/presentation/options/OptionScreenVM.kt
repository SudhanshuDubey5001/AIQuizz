package com.sudhanshu.aiquiz.feature_quiz.presentation.options

import androidx.lifecycle.ViewModel
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.domain.model.AIModel
import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.AIModelData
import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.QuizConfig
import com.sudhanshu.aiquiz.feature_quiz.presentation.utils.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OptionScreenVM @Inject constructor(
    private val quizConfig: QuizConfig,
    private val userData: UserData,
    private val aiModelData: AIModelData
) : ViewModel() {

    val config = quizConfig.configuration
    val user = userData.user

    fun changeModel(model: String){
        aiModelData.setModel(model)
    }

    fun getModel(): String{
        return aiModelData.getModel()
    }

    fun onEvents(event: OptionScreenEvents){
        when(event){
            is OptionScreenEvents.OnValueChangedNameInput -> {
                userData.setName(event.name)
            }

            is OptionScreenEvents.OnValueChangedQuestionsCount -> {
                quizConfig.setQuestionCount(event.count.toInt())
            }

            is OptionScreenEvents.OnDifficultyLevelChanged -> {
                quizConfig.setDifficultyLevel(event.level)
            }
        }
    }
}