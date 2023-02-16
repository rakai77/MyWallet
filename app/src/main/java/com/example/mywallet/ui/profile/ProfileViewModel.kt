package com.example.mywallet.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.ProfileDataResponse
import com.example.mywallet.data.repository.authentication.AuthenticationRepository
import com.example.mywallet.utils.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepo: AuthenticationRepository, private val pref: Preference) : ViewModel(){

    private val _getProfile = MutableLiveData<Response<ProfileDataResponse>>()
    val getProfile: LiveData<Response<ProfileDataResponse>> = _getProfile

    val token = pref.getToken()
    val getFirstName = pref.getFirstName()
    val getLastName = pref.getLastName()
    val getEmailUser = pref.getEmailUser()

    fun getProfile(token: String) {
        authRepo.getProfile(token).onEach { response ->
            when(response) {
                is Response.Loading -> {
                    _getProfile.value = Response.Loading
                }
                is Response.Success -> {
                    response.data.let {
                        _getProfile.value = Response.Success(it)
                    }
                }
                is Response.Error -> {
                    _getProfile.value = Response.Error(response.message, response.errorCode, response.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}