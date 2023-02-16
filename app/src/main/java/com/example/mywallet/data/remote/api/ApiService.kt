package com.example.mywallet.data.remote.api

import com.example.mywallet.data.remote.response.*
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    @FormUrlEncoded
    @POST("/registration")
    suspend fun registration(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse

    @GET("/getProfile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): ProfileDataResponse

    @FormUrlEncoded
    @POST("/updateProfile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String
    ): UpdateProfileResponse

    @FormUrlEncoded
    @POST("/transfer")
    suspend fun transfer(
        @Header("Authorization") token: String,
        @Field("amount") amount: Int,
    ): TransferResponse

    @FormUrlEncoded
    @POST("/topup")
    suspend fun topUp(
        @Header("Authorization") token: String,
        @Field("amount") amount: Int,
    ): TopUpResponse

    @GET("/balance")
    suspend fun getBalance(
        @Header("Authorization") token: String
    ): BalanceResponse

    @GET("/transactionHistory")
    suspend fun getTransactionHistory(
        @Header("Authorization") token: String
    ): TransactionHistoryResponse
}