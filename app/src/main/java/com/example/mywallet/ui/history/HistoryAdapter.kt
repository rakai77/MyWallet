package com.example.mywallet.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallet.data.remote.response.DataItem
import com.example.mywallet.databinding.ItemTransactionHistoryBinding
import com.example.mywallet.utils.Helper.currency
import com.example.mywallet.utils.Helper.formattingDate

class HistoryAdapter : ListAdapter<DataItem, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemTransactionHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class HistoryViewHolder(val binding: ItemTransactionHistoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: DataItem) {
            with(binding) {
                tvTransactionType.text = item.transactionType.toString()
                tvTransactionAmount.text = item.amount.toString().currency()
                tvDate.text = formattingDate(item.transactionTime)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<DataItem> =
            object : DiffUtil.ItemCallback<DataItem>() {
                override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                    return oldItem.transactionId == newItem.transactionId
                }

                override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                    return oldItem == newItem
                }
            }
    }
}