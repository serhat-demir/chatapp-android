package com.serhatd.chatapp.data.remote

import com.serhatd.chatapp.data.model.ApiResponse
import com.serhatd.chatapp.data.model.Message
import retrofit2.http.GET

interface MessageService {
    @GET("messages")
    suspend fun getMessages(): ApiResponse<List<Message>>
}