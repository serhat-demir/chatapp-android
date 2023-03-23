package com.serhatd.chatapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.serhatd.chatapp.data.model.ApiResponse
import com.serhatd.chatapp.data.model.UserRequest
import com.serhatd.chatapp.data.prefs.SharedPrefs
import com.serhatd.chatapp.data.repository.UserRepository
import com.serhatd.chatapp.ui.callback.NetworkCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val callback: NetworkCallback, private val prefs: SharedPrefs, private val repo: UserRepository): ViewModel() {
    val loginObserver = MutableLiveData<Boolean>()

    fun login(user_name: String, user_password: String) {
        val userReq = UserRequest(user_name, user_password)
        viewModelScope.launch {
            val response = repo.login(userReq)
            if (response.isSuccessful && response.body() != null && response.body()!!.data != null) {
                startSession(response.body()!!.data!!.user_name, response.body()!!.data!!.user_token)
                loginObserver.value = true
                loginObserver.value = false
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

    fun register(user_name: String, user_password: String) {
        val userReq = UserRequest(user_name, user_password)
        viewModelScope.launch {
            val response = repo.register(userReq)
            if (response.isSuccessful && response.body() != null) {
                callback.onSuccess(response.body()!!.message)
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

    private fun startSession(name: String, token: String) {
        val data = hashMapOf<String, String>()
        data[SharedPrefs.COL_USER_NAME] = name
        data[SharedPrefs.COL_USER_TOKEN] = token

        prefs.setSharedPreference(data)
    }
}