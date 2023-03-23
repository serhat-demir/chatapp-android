package com.serhatd.chatapp.data.repository

import com.serhatd.chatapp.data.model.ApiResponse
import com.serhatd.chatapp.data.model.Message
import com.serhatd.chatapp.data.remote.MessageService
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

class MessageRepository(private val service: MessageService) {
    suspend fun getMessages(token: String): Response<ApiResponse<List<Message>>> {
        return withContext(Dispatchers.IO) {
            service.getMessages(token)
        }
    }

    fun emitEvent(socket: Socket, eventName: String, data: JSONObject) {
        socket.emit(eventName, data)
    }

    fun listenForEvent(socket: Socket, eventName: String, listener: Emitter.Listener) {
        socket.on(eventName, listener)
    }
}