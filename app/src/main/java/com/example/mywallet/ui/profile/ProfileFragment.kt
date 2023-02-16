package com.example.mywallet.ui.profile

import android.content.Intent
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
import com.example.mywallet.databinding.FragmentProfileBinding
import com.example.mywallet.ui.authentication.AuthenticationActivity
import com.example.mywallet.utils.Helper
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel>()
    private var token: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentProfileBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        initObserver()
        logout()

        binding.tflUpdateProfile.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_updateProfileActivity)
        }
    }

    private fun logout() {
        binding.tflLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(requireContext(), AuthenticationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            Toast.makeText(
                requireContext(),
                "Logout Successfully",
                Toast.LENGTH_LONG
            ).show()
            activity?.finish()
        }
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

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getProfile("Bearer $token")
            }
        }
        viewModel.getProfile.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Response.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    binding.progressbar.visibility = View.GONE
                    val firstName = result.data.data?.firstName.toString()
                    val lastname = result.data.data?.lastName.toString()
                    val email = result.data.data?.email.toString()
                    binding.tvFirstName.text = firstName
                    binding.tvLastName.text = lastname
                    binding.tvEmail.text = email

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


    private fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getFirstName.collect {
                        binding.tvFirstName.text = it
                    }
                }
                launch {
                    viewModel.getLastName.collect {
                        binding.tvLastName.text = it
                    }
                }
                launch {
                    viewModel.getEmailUser.collect {
                        binding.tvEmail.text = it
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}