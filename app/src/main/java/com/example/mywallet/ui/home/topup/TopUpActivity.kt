package com.example.mywallet.ui.home.topup

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mywallet.R
import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.ErrorResponse
import com.example.mywallet.databinding.ActivityTopUpBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject

@AndroidEntryPoint
class TopUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopUpBinding
    private val viewModel by viewModels<TopUpViewModel>()

    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        setupAction()

    }

    private fun setupAction() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.token.collect {
                    token = it
                    Log.d("Check Token", token)
                }
            }
        }
        binding.apply {
            btnTopUp.setOnClickListener {
                viewModel.topUp(
                    "Bearer $token",
                    amount = binding.edtAmountTopup.text.toString().replace(",", "").toInt()
                )
            }
        }
    }


    private fun initObserver() {
        viewModel.topup.observe(this@TopUpActivity) { result ->
            when (result) {
                is Response.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(
                        this@TopUpActivity,
                        "${result.data.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                is Response.Error -> {
                    binding.progressbar.visibility = View.GONE
                    val errors = result.errorBody?.string()?.let { JSONObject(it).toString() }
                    val gson = Gson()
                    val jsonObject = gson.fromJson(errors, JsonObject::class.java)
                    val errorResponse = gson.fromJson(jsonObject, ErrorResponse::class.java)

                    Toast.makeText(
                        this@TopUpActivity,
                        "${errorResponse.status} ${errorResponse.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}