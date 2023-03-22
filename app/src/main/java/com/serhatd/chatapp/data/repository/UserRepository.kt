package com.serhatd.chatapp.data.repository

import com.serhatd.chatapp.data.model.ApiResponse
import com.serhatd.chatapp.data.model.User
import com.serhatd.chatapp.data.model.UserRequest
import com.serhatd.chatapp.data.remote.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository(private val service: UserService) {
    suspend fun login(user: UserRequest): Response<ApiResponse<User>> {
        return withContext(Dispatchers.IO) {
            service.login(user)
        }
    }

    suspend fun register(user: UserRequest): Response<ApiResponse<Nothing>> {
        return withContext(Dispatchers.IO) {
            service.register(user)
        }
    }
}