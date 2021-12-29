package com.example.testyogiyo.di

import android.content.Context
import androidx.room.Room
import com.example.testyogiyo.data.local.Dao.UserDao
import com.example.testyogiyo.data.local.UserInfoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context) :UserInfoDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            UserInfoDatabase::class.java,
            "user.db"
        ).build()

    @Singleton
    @Provides
    fun provideUserDao(userInfoDatabase : UserInfoDatabase) :UserDao =
        userInfoDatabase.userDao
}