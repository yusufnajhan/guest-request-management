package com.example.tasklistapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasklistapp.data.response.RequestResponseItem
import com.example.tasklistapp.data.response.RequestUpdate
import com.example.tasklistapp.data.response.ResponseUpdate
import com.example.tasklistapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateRequestViewModel() : ViewModel() {

    private val _isLoadingUpdate = MutableLiveData<Boolean>()
    val isLoadingUpdate: LiveData<Boolean> = _isLoadingUpdate

    private val _responseUpdate = MutableLiveData<ResponseUpdate>()
    val responseUpdate: LiveData<ResponseUpdate> = _responseUpdate


    fun updateRequestStatus(id: Int, status: String){

        _isLoadingUpdate.value = true
        val client = ApiConfig.getApiService().updateRequestStatus(id, RequestUpdate(status))

        client.enqueue(object : Callback<ResponseUpdate> {
            override fun onResponse(call: Call<ResponseUpdate>, response: Response<ResponseUpdate>) {
                _isLoadingUpdate.value = false
                if (response.isSuccessful) {
                    _responseUpdate.value = response.body()
                } else {
                    Log.e("UpdateRequestViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUpdate>, t: Throwable) {
                _isLoadingUpdate.value = false
                Log.e("UpdateRequestViewModel", "onFailure: ${t.message.toString()}")
            }
        })

    }
}