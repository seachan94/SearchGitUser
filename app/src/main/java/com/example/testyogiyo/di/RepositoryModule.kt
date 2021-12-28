package com.example.testyogiyo.di

import com.example.testyogiyo.data.remote.api.GithubApi
import com.example.testyogiyo.data.repository.GithubRepository
import com.example.testyogiyo.data.repository.GithubRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGithubRepository( api : GithubApi) : GithubRepository{
        return GithubRepositoryImpl(api)
    }
}