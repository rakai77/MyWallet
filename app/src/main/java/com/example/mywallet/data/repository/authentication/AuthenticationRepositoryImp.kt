package com.example.mywallet.data.repository.authentication

import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.api.ApiService
import com.example.mywallet.data.remote.response.LoginResponse
import com.example.mywallet.data.remote.response.ProfileDataResponse
import com.example.mywallet.data.remote.response.RegisterResponse
import com.example.mywallet.data.remote.response.UpdateProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(private val apiService: ApiService) :
    AuthenticationRepository {

    override fun login(email: String, password: String): Flow<Response<LoginResponse>> = flow {
        emit(Response.Loading)
        try {
            val result = apiService.login(email, password)
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

    override fun registration(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Flow<Response<RegisterResponse>> = flow {
        emit(Response.Loading)
        try {
            val result = apiService.registration(firstName, lastName, email, password)
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

    override fun getProfile(token: String): Flow<Response<ProfileDataResponse>> = flow {
        emit(Response.Loading)
        try {
            val result = apiService.getProfile(token)
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

    override fun updateProfile(
        token: String,
        firstName: String,
        lastName: String
    ): Flow<Response<UpdateProfileResponse>> = flow {
        emit(Response.Loading)
        try {
            val result = apiService.updateProfile(token, firstName, lastName)
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