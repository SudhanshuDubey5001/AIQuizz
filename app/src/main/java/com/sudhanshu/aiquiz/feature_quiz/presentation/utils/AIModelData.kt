package com.sudhanshu.aiquiz.feature_quiz.presentation.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.domain.model.AIModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIModelData @Inject constructor(){
    private val _aiModel = mutableStateOf(
        AIModel()
    )

    private val aiModel: State<AIModel> = _aiModel

    fun setModel(model: String) {
        _aiModel.value = _aiModel.value.copy(
            modelId = Utils.ModelId.fromName(model)?.id ?: Utils.ModelId.GROQ.id,
            modelName = model
        )
    }

    fun getModel() = _aiModel.value.modelName
    fun getModelId() = _aiModel.value.modelId
}