package com.example.testyogiyo.di

import android.content.Context
import androidx.room.Room
import com.example.testyogiyo.data.database.UserInfoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule {
    @Provides
    @Named("test_user_database")
    fun providesUserInMemoryDB(@ApplicationContext context : Context)=
        Room.inMemoryDatabaseBuilder(context,UserInfoDatabase::class.java)
            .allowMainThreadQueries()
            .build()

}