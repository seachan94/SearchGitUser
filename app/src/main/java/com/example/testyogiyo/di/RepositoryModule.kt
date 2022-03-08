package com.example.testyogiyo.di

import com.example.testyogiyo.data.database.dao.UserDao
import com.example.testyogiyo.data.database.repository.LocalUserRepository
import com.example.testyogiyo.data.database.repository.LocalUserRepositoryImpl
import com.example.testyogiyo.data.remote.GithubApi
import com.example.testyogiyo.data.remote.repository.UserRepository
import com.example.testyogiyo.data.remote.repository.UserRepositoryImpl
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
    fun providesRemoteUserRepository(api : GithubApi) : UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesLocalUserRepository(dao : UserDao) : LocalUserRepository =
        LocalUserRepositoryImpl(dao)
}