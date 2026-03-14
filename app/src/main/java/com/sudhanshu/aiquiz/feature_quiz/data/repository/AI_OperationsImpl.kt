package com.sudhanshu.aiquiz.feature_quiz.data.repository


import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.Gson
import com.sudhanshu.aiquiz.core.utils.AppConfigurationConstants
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Message
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Post
import com.sudhanshu.aiquiz.feature_quiz.domain.repository.AI_Operations
import com.sudhanshu.aiquiz.feature_quiz.domain.repository.ApiService
import jakarta.inject.Inject

class AI_OperationsImpl @Inject constructor(
    private val apiService: ApiService
) : AI_Operations{
    override suspend fun gAI_generateAIResponse(prompt: String) : String{
        val request = Post(
            model = AppConfigurationConstants.AI_MODEL,
            messages = listOf(
                Message("user", prompt)
            )
        )

        val json = Gson().toJson(request)
        Log.d(TAG,json.toString())
        val response = apiService.chatCompletion(request)
        Log.d(TAG,response.toString())
        Log.d(TAG,response.errorBody().toString())
        return response.body()?.choices?.first()?.message?.content ?: ""
    }
}