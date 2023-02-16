package com.example.mywallet.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.LoginResponse
import com.example.mywallet.data.remote.response.RegisterResponse
import com.example.mywallet.data.repository.authentication.AuthenticationRepository
import com.example.mywallet.utils.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val pref: Preference, private val authenticationRepo: AuthenticationRepository) : ViewModel(){

    private val _loginState = MutableLiveData<Response<LoginResponse>>()
    val loginState: LiveData<Response<LoginResponse>> = _loginState


    private val _registerState = MutableLiveData<Response<RegisterResponse>>()
    val registerState: LiveData<Response<RegisterResponse>> = _registerState

    val getToken = pref.getToken()

    fun login(email: String, password: String) {
        authenticationRepo.login(email, password).onEach { result ->
            when(result) {
                is Response.Loading -> {
                    _loginState.value = Response.Loading
                }
                is Response.Success -> {
                    result.data.let {
                        _loginState.value = Response.Success(it)
                        pref.saveToken(it.data?.token.toString())
                        pref.saveFirstName(it.data?.firstName.toString())
                        pref.saveLastName(it.data?.lastName.toString())
                        pref.saveEmailUser(it.data?.email.toString())
                    }
                }
                is Response.Error -> {
                    _loginState.value = Response.Error(result.message, result.errorCode, result.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun registration(firstName: String, lastName: String, email: String, password: String) {
        authenticationRepo.registration(firstName, lastName, email, password).onEach { result ->
            when(result) {
                is Response.Loading -> {
                    _registerState.value = Response.Loading
                }
                is Response.Success -> {
                    result.data.let {
                        _registerState.value = Response.Success(it)
                    }
                }
                is Response.Error -> {
                    _registerState.value = Response.Error(result.message, result.errorCode, result.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }

}