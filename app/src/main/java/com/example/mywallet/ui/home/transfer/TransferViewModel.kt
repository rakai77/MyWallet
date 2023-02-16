package com.example.mywallet.ui.home.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.TransferResponse
import com.example.mywallet.data.repository.wallet.WalletRepository
import com.example.mywallet.utils.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(private val repository: WalletRepository, pref: Preference) : ViewModel(){

    private val _transfer = MutableLiveData<Response<TransferResponse>>()
    val transfer: LiveData<Response<TransferResponse>> = _transfer

    val token = pref.getToken()

    fun transfer(token: String, amount: Int) {
        repository.transfer(token, amount).onEach { response ->
            when(response) {
                is Response.Loading -> {
                    _transfer.value = Response.Loading
                }
                is Response.Success -> {
                    response.data.let {
                        _transfer.value = Response.Success(it)
                    }
                }
                is Response.Error -> {
                    _transfer.value = Response.Error(response.message, response.errorCode, response.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }
}