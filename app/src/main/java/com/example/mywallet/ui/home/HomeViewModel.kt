package com.example.mywallet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.BalanceResponse
import com.example.mywallet.data.remote.response.TopUpResponse
import com.example.mywallet.data.repository.wallet.WalletRepository
import com.example.mywallet.utils.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WalletRepository,
    pref: Preference
) : ViewModel() {

    private val _balance = MutableLiveData<Response<BalanceResponse>>()
    val balance: LiveData<Response<BalanceResponse>> = _balance

    val token = pref.getToken()
    val getFirstName = pref.getFirstName()
    val getLastName = pref.getLastName()

    fun getBalance(token: String) {
        repository.getBalance(token).onEach { response ->
            when (response) {
                is Response.Loading -> {
                    _balance.value = Response.Loading
                }
                is Response.Success -> {
                    response.data.let {
                        _balance.value = Response.Success(it)
                    }
                }
                is Response.Error -> {
                    _balance.value = Response.Error(response.message, response.errorCode, response.errorBody)
                }
            }
        }.launchIn(viewModelScope)

    }

}