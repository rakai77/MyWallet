package com.example.mywallet.ui.home.topup

import androidx.lifecycle.*
import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.TopUpResponse
import com.example.mywallet.data.repository.wallet.WalletRepository
import com.example.mywallet.utils.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TopUpViewModel @Inject constructor(private val repository: WalletRepository, pref: Preference) : ViewModel() {

    private val _topup = MutableLiveData<Response<TopUpResponse>>()
    val topup: LiveData<Response<TopUpResponse>> = _topup

    val token = pref.getToken()


    fun topUp(token: String, amount: Int) {
        repository.topUp(token, amount).onEach { response ->
            when(response) {
                is Response.Loading -> {
                    _topup.value = Response.Loading
                }
                is Response.Success -> {
                    response.data.let {
                        _topup.value = Response.Success(it)
                    }
                }
                is Response.Error -> {
                    _topup.value = Response.Error(response.message, response.errorCode, response.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }



}