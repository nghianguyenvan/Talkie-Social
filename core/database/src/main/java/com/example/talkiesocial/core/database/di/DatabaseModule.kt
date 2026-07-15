package com.example.talkiesocial.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.talkiesocial.core.database.TalkieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTalkieDatabase(
        @ApplicationContext context: Context
    ): TalkieDatabase = Room.databaseBuilder(
        context,
        TalkieDatabase::class.java,
        "talkie-database"
    ).build()
}
