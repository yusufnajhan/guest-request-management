package com.example.tasklistapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasklistapp.data.response.RequestBody
import com.example.tasklistapp.data.response.ResponseUpdate
import com.example.tasklistapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateRequestViewModel : ViewModel() {
    private val _isLoadingCreate = MutableLiveData<Boolean>()
    val isLoadingCreate: LiveData<Boolean> = _isLoadingCreate

    private val _responseCreate = MutableLiveData<ResponseUpdate>()
    val responseCreate: LiveData<ResponseUpdate> = _responseCreate

    fun createRequest(message: String) {
        _isLoadingCreate.value = true
        val client = ApiConfig.getApiService().createRequest(RequestBody(message))

        client.enqueue(object : Callback<ResponseUpdate> {
            override fun onResponse(call: Call<ResponseUpdate>, response: Response<ResponseUpdate>) {
                _isLoadingCreate.value = false
                if (response.isSuccessful) {
                    _responseCreate.value = response.body()
                } else {
                    Log.e("CreateRequestViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUpdate>, t: Throwable) {
                _isLoadingCreate.value = false
                Log.e("CreateRequestViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }
}
