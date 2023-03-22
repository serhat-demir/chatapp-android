package com.serhatd.chatapp.data.remote

import com.serhatd.chatapp.data.model.ApiResponse
import com.serhatd.chatapp.data.model.Message
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface MessageService {
    @GET("messages")
    suspend fun getMessages(
        @Header("X-Auth-Token") token: String
    ): Response<ApiResponse<List<Message>>>
}