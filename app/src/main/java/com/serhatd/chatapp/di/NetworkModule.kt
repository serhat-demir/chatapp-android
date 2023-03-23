package com.serhatd.chatapp.di

import android.content.Context
import com.serhatd.chatapp.data.remote.MessageService
import com.serhatd.chatapp.data.remote.UserService
import com.serhatd.chatapp.ui.callback.NetworkCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.39:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideMessageService(retrofit: Retrofit): MessageService {
        return retrofit.create(MessageService::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkCallback(@ApplicationContext context: Context): NetworkCallback {
        return NetworkCallback(context)
    }

    @Singleton
    @Provides
    fun provideSocket(): Socket {
        return IO.socket("http://192.168.1.39:3000").connect()
    }
}