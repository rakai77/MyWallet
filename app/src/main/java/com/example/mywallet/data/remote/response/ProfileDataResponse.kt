package com.example.mywallet.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileDataResponse(

	@field:SerializedName("data")
	val data: DataProfile?,

	@field:SerializedName("message")
	val message: String?,

	@field:SerializedName("status")
	val status: Int?
)

data class DataProfile(

	@field:SerializedName("last_name")
	val lastName: String?,

	@field:SerializedName("first_name")
	val firstName: String?,

	@field:SerializedName("email")
	val email: String?
)
