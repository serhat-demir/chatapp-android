package com.serhatd.chatapp.data.model

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("message")
    val message: String,

    @SerializedName("sender")
    val sender: String
)