package com.example.mywallet.data.repository.authentication

import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.LoginResponse
import com.example.mywallet.data.remote.response.ProfileDataResponse
import com.example.mywallet.data.remote.response.RegisterResponse
import com.example.mywallet.data.remote.response.UpdateProfileResponse
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun login(email: String, password: String) : Flow<Response<LoginResponse>>

    fun registration(firstName: String, lastName: String, email: String, password: String) : Flow<Response<RegisterResponse>>

    fun getProfile(token: String) : Flow<Response<ProfileDataResponse>>

    fun updateProfile(token: String, firstName: String, lastName: String) : Flow<Response<UpdateProfileResponse>>

}