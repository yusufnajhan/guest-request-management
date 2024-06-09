package com.example.tasklistapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasklistapp.data.response.TaskResponse
import com.example.tasklistapp.data.response.UserItem
import com.example.tasklistapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<UserItem>>()
    val listUser: LiveData<List<UserItem>> = _listUser

    init {
        findListUsers()
    }

    fun findListUsers(username: String? = null){
        _isLoading.value = true
        val usernameSet = username ?: "aba"
        val client = ApiConfig.getApiService().getListUsers(usernameSet)
        client.enqueue(object : Callback<TaskResponse> {
            override fun onResponse(call: Call<TaskResponse>, response: Response<TaskResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })

    }
}