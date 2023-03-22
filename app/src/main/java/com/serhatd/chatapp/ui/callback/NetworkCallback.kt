package com.serhatd.chatapp.ui.callback

import android.content.Context
import android.widget.Toast

class NetworkCallback(private val context: Context) {
    fun onSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}