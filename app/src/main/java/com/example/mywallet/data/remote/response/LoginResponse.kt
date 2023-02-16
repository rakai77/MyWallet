package com.example.mywallet.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: DataUser?,

	@field:SerializedName("message")
	val message: String?,

	@field:SerializedName("status")
	val status: Int?
)

data class DataUser(

	@field:SerializedName("last_name")
	val lastName: String?,

	@field:SerializedName("first_name")
	val firstName: String?,

	@field:SerializedName("email")
	val email: String?,

	@field:SerializedName("token")
	val token: String?
)
