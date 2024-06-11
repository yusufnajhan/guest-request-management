package com.example.tasklistapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasklistapp.data.response.RequestResponse
import com.example.tasklistapp.data.response.RequestResponseItem
import com.example.tasklistapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListTaskViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listTask = MutableLiveData<List<RequestResponseItem>>()
    val listTask: LiveData<List<RequestResponseItem>> = _listTask

    init {
        findListTasks()
    }

    fun findListTasks(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListTasks()
        client.enqueue(object : Callback<List<RequestResponseItem>> {
            override fun onResponse(call: Call<List<RequestResponseItem>>, response: Response<List<RequestResponseItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listTask.value = response.body()
                } else {
                    Log.e("ListTaskViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RequestResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("ListTaskViewModel", "onFailure: ${t.message.toString()}")
            }
        })

    }
}