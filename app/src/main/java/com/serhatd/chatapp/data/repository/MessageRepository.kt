package com.serhatd.chatapp.data.repository

import com.serhatd.chatapp.data.model.ApiResponse
import com.serhatd.chatapp.data.model.Message
import com.serhatd.chatapp.data.remote.MessageService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MessageRepository(private val service: MessageService) {
    suspend fun getMessages(token: String): Response<ApiResponse<List<Message>>> {
        return withContext(Dispatchers.IO) {
            service.getMessages(token)
        }
    }
}