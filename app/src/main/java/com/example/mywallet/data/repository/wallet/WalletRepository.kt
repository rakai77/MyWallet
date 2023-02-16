package com.example.mywallet.data.repository.wallet

import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.BalanceResponse
import com.example.mywallet.data.remote.response.TopUpResponse
import com.example.mywallet.data.remote.response.TransactionHistoryResponse
import com.example.mywallet.data.remote.response.TransferResponse
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    fun topUp(token: String, amount: Int) : Flow<Response<TopUpResponse>>

    fun transfer(token: String, amount: Int) : Flow<Response<TransferResponse>>

    fun getBalance(token: String): Flow<Response<BalanceResponse>>

    fun getTransactionHistory(token: String) : Flow<Response<TransactionHistoryResponse>>
}