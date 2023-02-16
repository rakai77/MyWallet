package com.example.mywallet.data.repository.wallet

import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.api.ApiService
import com.example.mywallet.data.remote.response.BalanceResponse
import com.example.mywallet.data.remote.response.TopUpResponse
import com.example.mywallet.data.remote.response.TransactionHistoryResponse
import com.example.mywallet.data.remote.response.TransferResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class WalletRepositoryImp @Inject constructor(private val apiService: ApiService) : WalletRepository {

    override fun topUp(token: String, amount: Int): Flow<Response<TopUpResponse>> = flow {
        emit(Response.Loading)
        try {
            val result = apiService.topUp(token, amount)
            emit(Response.Success(result))
        } catch (t: Throwable) {
            if (t is HttpException) {
                when (t.code()) {
                    400 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    401 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    404 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    500 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    else -> emit(Response.Error(null, null, null))
                }
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage, null, null))
        }
    }

    override fun transfer(token: String, amount: Int): Flow<Response<TransferResponse>> = flow {
        emit(Response.Loading)
        try {
            val result = apiService.transfer(token, amount)
            emit(Response.Success(result))
        } catch (t: Throwable) {
            if (t is HttpException) {
                when (t.code()) {
                    400 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    401 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    404 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    500 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    else -> emit(Response.Error(null, null, null))
                }
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage, null, null))
        }
    }

    override fun getBalance(token: String): Flow<Response<BalanceResponse>> = flow {
        emit(Response.Loading)
        try {
            val result = apiService.getBalance(token)
            emit(Response.Success(result))
        } catch (t: Throwable) {
            if (t is HttpException) {
                when (t.code()) {
                    400 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    401 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    404 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    500 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    else -> emit(Response.Error(null, null, null))
                }
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage, null, null))
        }
    }

    override fun getTransactionHistory(token: String): Flow<Response<TransactionHistoryResponse>> = flow {
        emit(Response.Loading)
        try {
            val result = apiService.getTransactionHistory(token)
            emit(Response.Success(result))
        } catch (t: Throwable) {
            if (t is HttpException) {
                when (t.code()) {
                    400 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    401 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    404 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    500 -> emit(Response.Error(t.message(), t.code(), t.response()?.errorBody()))
                    else -> emit(Response.Error(null, null, null))
                }
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage, null, null))
        }
    }

}