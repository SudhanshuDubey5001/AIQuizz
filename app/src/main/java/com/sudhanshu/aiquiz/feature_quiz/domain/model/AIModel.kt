package com.sudhanshu.aiquiz.feature_quiz.domain.model

import com.sudhanshu.aiquiz.core.utils.Utils

data class AIModel(
    val modelId: String = Utils.ModelId.GROQ.id,
    val modelName: String = Utils.ModelId.GROQ.name
)
