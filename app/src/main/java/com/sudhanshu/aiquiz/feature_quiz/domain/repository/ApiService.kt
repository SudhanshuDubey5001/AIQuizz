package com.sudhanshu.aiquiz.feature_quiz.domain.repository
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Post
import com.sudhanshu.aiquiz.feature_quiz.domain.model.AIResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("chat/completions")
    suspend fun chatCompletion(
        @Body request: Post
    ): Response<AIResponse>
}