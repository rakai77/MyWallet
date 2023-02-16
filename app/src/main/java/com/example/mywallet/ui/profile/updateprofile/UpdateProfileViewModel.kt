package com.example.mywallet.ui.profile.updateprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.TransferResponse
import com.example.mywallet.data.remote.response.UpdateProfileResponse
import com.example.mywallet.data.repository.authentication.AuthenticationRepository
import com.example.mywallet.data.repository.wallet.WalletRepository
import com.example.mywallet.utils.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(private val repository: AuthenticationRepository, pref: Preference) : ViewModel(){

    private val _updateProfile = MutableLiveData<Response<UpdateProfileResponse>>()
    val updateProfile: LiveData<Response<UpdateProfileResponse>> = _updateProfile

    val token = pref.getToken()

    fun transfer(token: String, firstName: String, lastName: String) {
        repository.updateProfile(token, firstName, lastName).onEach { response ->
            when(response) {
                is Response.Loading -> {
                    _updateProfile.value = Response.Loading
                }
                is Response.Success -> {
                    response.data.let {
                        _updateProfile.value = Response.Success(it)
                    }
                }
                is Response.Error -> {
                    _updateProfile.value = Response.Error(response.message, response.errorCode, response.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }
}