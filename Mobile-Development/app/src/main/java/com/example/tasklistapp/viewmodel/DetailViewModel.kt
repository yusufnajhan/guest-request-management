package com.example.tasklistapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasklistapp.data.response.RequestResponseItem
import com.example.tasklistapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel() : ViewModel(){
    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    private val _detailTask = MutableLiveData<RequestResponseItem>()
    val detailTask: LiveData<RequestResponseItem> = _detailTask


    fun findDetailTask(id: Int){

        _isLoadingDetail.value = true
        val client = ApiConfig.getApiService().getDetailRequests(id)

        client.enqueue(object : Callback<List<RequestResponseItem>> {
            override fun onResponse(call: Call<List<RequestResponseItem>>, response: Response<List<RequestResponseItem>>) {
                _isLoadingDetail.value = false
                if (response.isSuccessful) {
                    val bodyJson = response.body()
                    _detailTask.value = bodyJson?.get(0)
                } else {
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RequestResponseItem>>, t: Throwable) {
                _isLoadingDetail.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message.toString()}")
            }
        })

    }
}