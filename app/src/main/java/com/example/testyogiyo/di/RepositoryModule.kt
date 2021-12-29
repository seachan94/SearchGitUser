package com.example.testyogiyo.di

import com.example.testyogiyo.data.local.Dao.UserDao
import com.example.testyogiyo.data.remote.api.GithubApi
import com.example.testyogiyo.data.repository.UserRepository
import com.example.testyogiyo.data.repository.UserRepositoryImpl
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
    fun provideGithubRepository( api : GithubApi,dao : UserDao) : UserRepository{
        return UserRepositoryImpl(api,dao)
    }
}