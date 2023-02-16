package com.example.mywallet.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(

	@field:SerializedName("data")
	val data: DataUpdateProfile?,

	@field:SerializedName("message")
	val message: String?,

	@field:SerializedName("status")
	val status: Int?
)

data class DataUpdateProfile(

	@field:SerializedName("last_name")
	val lastName: String?,

	@field:SerializedName("first_name")
	val firstName: String?
)
