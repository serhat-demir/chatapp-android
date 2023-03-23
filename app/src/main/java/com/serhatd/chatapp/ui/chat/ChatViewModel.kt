package com.serhatd.chatapp.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.serhatd.chatapp.data.model.ApiResponse
import com.serhatd.chatapp.data.model.Message
import com.serhatd.chatapp.data.prefs.SharedPrefs
import com.serhatd.chatapp.data.repository.MessageRepository
import com.serhatd.chatapp.ui.callback.NetworkCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val callback: NetworkCallback, private val socket: Socket, private val prefs: SharedPrefs, private val repo: MessageRepository): ViewModel() {
    val messages = MutableLiveData<List<Message>>()
    val terminateSessionObserver = MutableLiveData<Boolean>()

    init {
        repo.listenForEvent(socket, "message") { args ->
            viewModelScope.launch(Dispatchers.Main) {
                val data: JSONObject = args[0] as JSONObject
                val message = Message(data.getString("message_text"), data.getString("message_sender"))

                if (messages.value == null) {
                    messages.value = listOf<Message>().toMutableList().plus(message)
                } else {
                    messages.value = messages.value!!.toMutableList().plus(message)
                }
            }
        }

        repo.listenForEvent(socket, "error") { args ->
            viewModelScope.launch(Dispatchers.Main) {
                val data: JSONObject = args[0] as JSONObject
                val message = data.getString("message")
                callback.onError(message)

                terminateSessionObserver.value = true
                terminateSessionObserver.value = false
            }
        }
    }

    fun getMessages() {
        val token = getSession()[SharedPrefs.COL_USER_TOKEN]
        if (token!!.isNotEmpty()) {
            viewModelScope.launch {
                val response = repo.getMessages(token)
                if (response.isSuccessful && response.body() != null && response.body()!!.data != null) {
                    messages.value = response.body()!!.data!!
                } else {
                    if (!response.isSuccessful || response.body() == null) {
                        val error = Gson().fromJson(response.errorBody()!!.string(), ApiResponse::class.java)
                        callback.onError(error.message)
                    } else {
                        callback.onError(response.body()!!.message)
                    }
                }
            }
        }
    }

    fun getSession(): HashMap<String, String> {
        val data = hashMapOf<String, String>()
        data[SharedPrefs.COL_USER_NAME] = prefs.getSharedPreference(SharedPrefs.COL_USER_NAME, "")
        data[SharedPrefs.COL_USER_TOKEN] = prefs.getSharedPreference(SharedPrefs.COL_USER_TOKEN, "")

        return data
    }

    fun endSession() {
        prefs.removeSharedPreference(arrayOf(SharedPrefs.COL_USER_NAME, SharedPrefs.COL_USER_TOKEN))
    }

    override fun onCleared() {
        super.onCleared()
        socket.disconnect()
        socket.off("message")
        socket.off("error")
    }
}