package com.serhatd.chatapp.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val user_id: Int,

    @SerializedName("user_name")
    val user_name: String,

    @SerializedName("user_password")
    val user_password: String,

    @SerializedName("user_token")
    val user_token: String
)