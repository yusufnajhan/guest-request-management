package com.example.tasklistapp.data.response

import com.google.gson.annotations.SerializedName

data class ResponseCreate(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("error")
	val error: String? = null
)
