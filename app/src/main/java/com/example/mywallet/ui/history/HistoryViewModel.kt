package com.example.mywallet.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.TransactionHistoryResponse
import com.example.mywallet.data.repository.wallet.WalletRepository
import com.example.mywallet.utils.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: WalletRepository, pref: Preference) : ViewModel() {

    private val _history = MutableLiveData<Response<TransactionHistoryResponse>>()
    val history: LiveData<Response<TransactionHistoryResponse>> = _history

    val token = pref.getToken()

    fun getTransactionHistory(token: String) {
        repository.getTransactionHistory(token).onEach { response ->
            when(response) {
                is Response.Loading -> {
                    _history.value = Response.Loading
                }
                is Response.Success -> {
                    response.data.let {
                        _history.value = Response.Success(it)
                    }
                }
                is Response.Error -> {
                    _history.value = Response.Error(response.message, response.errorCode, response.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }

}