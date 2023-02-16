package com.example.mywallet.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: Data?,

	@field:SerializedName("message")
	val message: String?,

	@field:SerializedName("status")
	val status: Int?
)

data class Data(

	@field:SerializedName("last_name")
	val lastName: String?,

	@field:SerializedName("first_name")
	val firstName: String?,

	@field:SerializedName("email")
	val email: String?
)
