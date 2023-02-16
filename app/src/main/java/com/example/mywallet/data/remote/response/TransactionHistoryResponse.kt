package com.example.mywallet.data.remote.response

import com.google.gson.annotations.SerializedName

data class TransactionHistoryResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String?,

	@field:SerializedName("status")
	val status: Int?
)

data class DataItem(

	@field:SerializedName("transaction_id")
	val transactionId: String?,

	@field:SerializedName("amount")
	val amount: Int?,

	@field:SerializedName("transaction_time")
	val transactionTime: String?,

	@field:SerializedName("transaction_type")
	val transactionType: String?
)
