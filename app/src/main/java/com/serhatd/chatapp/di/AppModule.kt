package com.serhatd.chatapp.di

import android.content.Context
import com.serhatd.chatapp.data.prefs.SharedPrefs
import com.serhatd.chatapp.data.remote.MessageService
import com.serhatd.chatapp.data.remote.UserService
import com.serhatd.chatapp.data.repository.MessageRepository
import com.serhatd.chatapp.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPrefs {
        return SharedPrefs(context)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userService: UserService): UserRepository {
        return UserRepository(userService)
    }

    @Singleton
    @Provides
    fun provideMessageRepository(messageService: MessageService): MessageRepository {
        return MessageRepository(messageService)
    }
}