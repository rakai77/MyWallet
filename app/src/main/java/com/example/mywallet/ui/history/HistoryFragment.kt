package com.example.mywallet.ui.history

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywallet.data.Response
import com.example.mywallet.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HistoryViewModel>()
    private lateinit var adapter: HistoryAdapter
    private var token: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupAction()
        initObserve()
    }

    private fun setupAction() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.token.collect {
                    token = it
                    Log.d("Check token", token)
                }
            }
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getTransactionHistory("Bearer $token")
            }
        }
        viewModel.history.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Response.Loading -> {
                    binding.apply {
                        shimmerHistory.root.startShimmer()
                        shimmerHistory.root.visibility = View.VISIBLE
                        rvHistory.visibility = View.GONE
                        emptyState.root.visibility = View.GONE
                    }
                }
                is Response.Success -> {
                    if (result.data.data.isNotEmpty()) {
                        binding.apply {
                            shimmerHistory.root.stopShimmer()
                            shimmerHistory.root.visibility = View.GONE
                            rvHistory.visibility = View.VISIBLE
                            emptyState.root.visibility = View.GONE
                            result.data.data.let {
                                adapter.submitList(it)
                            }
                            Log.d("Check RV", "$result")
                        }

                        Toast.makeText(
                            requireActivity(),
                            "${result.data.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.apply {
                            shimmerHistory.root.stopShimmer()
                            shimmerHistory.root.visibility = View.GONE
                            rvHistory.visibility = View.GONE
                            emptyState.root.visibility = View.VISIBLE
                        }
                    }
                }
                is Response.Error -> {
                    binding.apply {
                        shimmerHistory.root.stopShimmer()
                        shimmerHistory.root.visibility = View.GONE
                        rvHistory.visibility = View.GONE
                        emptyState.root.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            adapter = HistoryAdapter()
            rvHistory.adapter = adapter
            rvHistory.layoutManager = LinearLayoutManager(requireContext())
            rvHistory.setHasFixedSize(true)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}