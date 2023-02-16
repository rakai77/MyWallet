package com.example.mywallet.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("data")
	val data: Any,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)
