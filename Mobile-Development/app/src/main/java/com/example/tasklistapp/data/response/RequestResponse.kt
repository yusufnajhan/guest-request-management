package com.example.tasklistapp.data.response

import com.google.gson.annotations.SerializedName

data class RequestResponse(

	@field:SerializedName("RequestResponse")
	val requestResponse: List<RequestResponseItem>
)

data class RequestResponseItem(

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("status")
	val status: String
)
