package com.serhatd.chatapp.data.model

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("user_name")
    val user_name: String,

    @SerializedName("user_password")
    val user_password: String
)