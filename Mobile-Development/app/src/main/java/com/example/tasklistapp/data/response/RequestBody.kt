package com.example.tasklistapp.data.response

data class RequestBody(
    val message: String,
    val category: String = "request_onsite",
    val status: String = "new"
)
