package com.example.mywallet.data.remote.response

import com.google.gson.annotations.SerializedName

data class BalanceResponse(

	@field:SerializedName("data")
	val data: DataBalance?,

	@field:SerializedName("message")
	val message: String?,

	@field:SerializedName("status")
	val status: Int?
)

data class DataBalance(

	@field:SerializedName("balance")
	val balance: Int?
)
