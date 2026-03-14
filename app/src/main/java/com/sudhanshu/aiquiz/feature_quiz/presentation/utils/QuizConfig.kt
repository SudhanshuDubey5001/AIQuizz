package com.sudhanshu.aiquiz.feature_quiz.presentation.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Configuration
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Level
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizConfig @Inject constructor() {

    private val _topicsSelected = mutableStateListOf<String>()

    private val _config = mutableStateOf(
        Configuration(
            topics = _topicsSelected
        )
    )
    val configuration: State<Configuration> = _config

    fun addTopic(topic: String) {
        if (!configuration.value.topics.contains(topic)) {
            _topicsSelected.add(topic)
        }
    }

    fun removeTopic(index: Int) {
        _topicsSelected.removeAt(index)
    }

    fun setDifficultyLevel(level: Level) {
        _config.value = _config.value.copy(
            level = level
        )
    }

    fun setQuestionCount(count: Int) {
        _config.value = _config.value.copy(
            questionsCount = count
        )
    }
    fun resetData(){
        _topicsSelected.clear()
        _config.value = Configuration(
            topics = _topicsSelected
        )
    }
}