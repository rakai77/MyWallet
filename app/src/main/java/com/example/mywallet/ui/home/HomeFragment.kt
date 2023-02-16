package com.example.mywallet.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mywallet.R
import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.ErrorResponse
import com.example.mywallet.databinding.FragmentHomeBinding
import com.example.mywallet.utils.Helper
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private var token: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTopUp.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_topUpActivity)
        }

        binding.btnTransfer.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_transferActivity)
        }

        initData()
        setupAction()
        initObserver()

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
    }

    private fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getFirstName.collect {
                        binding.tvNameUser.text = it
                    }
                }
                launch {
                    viewModel.getLastName.collect {
                        binding.tvLastNameUser.text = it
                    }
                }
            }
        }
    }

    private fun initObserver() {
        binding.edtBalance.setText("0")
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getBalance("Bearer $token")
            }
        }
        viewModel.balance.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Response.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    binding.progressbar.visibility = View.GONE
                    if (result.data.data != null) {
                        val balance =
                            Helper.toRupiah(result.data.data.balance!!).substringAfter("Rp")
                        binding.edtBalance.setText(balance)
                    } else {
                        binding.edtBalance.setText("0")
                    }
                }
                is Response.Error -> {
                    binding.progressbar.visibility = View.GONE
                    val errors = result.errorBody?.string()?.let { JSONObject(it).toString() }
                    val gson = Gson()
                    val jsonObject = gson.fromJson(errors, JsonObject::class.java)
                    val errorResponse = gson.fromJson(jsonObject, ErrorResponse::class.java)

                    Toast.makeText(
                        requireContext(),
                        "${errorResponse.status} ${errorResponse.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}