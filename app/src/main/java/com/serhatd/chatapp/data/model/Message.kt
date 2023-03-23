package com.serhatd.chatapp.data.model

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("message_text")
    val message_text: String,

    @SerializedName("message_sender")
    val message_sender: String
)