package com.serhatd.chatapp.data.remote

import com.serhatd.chatapp.data.model.ApiResponse
import com.serhatd.chatapp.data.model.User
import com.serhatd.chatapp.data.model.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("login")
    suspend fun login(
        @Body user: UserRequest
    ): Response<ApiResponse<User>>

    @POST("register")
    suspend fun register(
        @Body user: UserRequest
    ): Response<ApiResponse<Nothing>>
}