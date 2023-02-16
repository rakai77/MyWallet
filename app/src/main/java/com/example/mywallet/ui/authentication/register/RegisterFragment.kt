package com.example.mywallet.ui.authentication.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mywallet.MainActivity
import com.example.mywallet.R
import com.example.mywallet.data.Response
import com.example.mywallet.data.remote.response.ErrorResponse
import com.example.mywallet.databinding.FragmentLoginBinding
import com.example.mywallet.databinding.FragmentRegisterBinding
import com.example.mywallet.ui.authentication.AuthenticationViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AuthenticationViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        setupAction()
    }


    private fun setupForm() : Boolean{
        var form = false

        val firstName = binding.edtFirstName.text.toString()
        val lastName = binding.edtLastName.text.toString()
        val email = binding.edtEmailRegister.text.toString()
        val password = binding.edtPasswordRegister.text.toString()

        when {
            firstName.isEmpty() -> {
                binding.edtFirstName.error = "Please fill your first name."
            }
            lastName.isEmpty() -> {
                binding.edtLastName.error = "Please fill your last name."
            }
            email.isEmpty() -> {
                binding.tflEmailRegister.error = "Please fill your email address."
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tflEmailRegister.error = "Please enter a valid email address."
            }
            password.isEmpty() -> {
                binding.tflPasswordRegister.error = "Please enter your password."
            }
            password.length < 6 -> {
                binding.tflPasswordRegister.error = "Password length must be 6 character."
            }
            else -> form = true
        }
        return form
    }

    private fun initObserver() {
        viewModel.registerState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Response.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Login Successfully ${result.data.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
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

    private fun setupAction() {
        binding.apply {
            btnRegister.setOnClickListener {
                if (setupForm()) {
                    viewModel.registration(
                        firstName = edtFirstName.text.toString(),
                        lastName = edtLastName.text.toString(),
                        email = edtEmailRegister.text.toString(),
                        password = edtPasswordRegister.text.toString(),
                    )
                }
            }
            binding.btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}