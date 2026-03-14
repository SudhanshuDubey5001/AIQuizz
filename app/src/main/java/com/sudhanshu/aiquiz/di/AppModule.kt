package com.sudhanshu.aiquiz.di

import com.sudhanshu.aiquiz.core.utils.APISecret
import com.sudhanshu.aiquiz.core.utils.AppConfigurationConstants
import com.sudhanshu.aiquiz.feature_quiz.data.repository.AI_OperationsImpl
import com.sudhanshu.aiquiz.feature_quiz.domain.repository.AI_Operations
import com.sudhanshu.aiquiz.feature_quiz.domain.repository.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(APISecret.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideAIOperations(
        apiService: ApiService
    ): AI_Operations {
        return AI_OperationsImpl(apiService)
    }

}