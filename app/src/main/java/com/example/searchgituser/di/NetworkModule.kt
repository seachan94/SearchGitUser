package com.example.searchgituser.di

import com.example.searchgituser.data.remote.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder().apply{
            addConverterFactory(GsonConverterFactory.create())
            baseUrl("https://api.github.com/")
        }.build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) : GithubApi =
        retrofit.create(GithubApi::class.java)

}