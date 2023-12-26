package com.example.financeapplication.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.entities.TransactionDTO
import com.example.financeapplication.R
import com.example.financeapplication.databinding.HistoryItemBinding

class HistoryAdapter( var transactions : List<TransactionDTO>) : Adapter<HistoryAdapter.HistoryViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemBinding : HistoryItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.history_item,
            parent,
            false
        )
        return HistoryViewHolder(itemBinding)

    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = transactions.get(position)
        holder.bind(item)
    }

    class HistoryViewHolder(val itemBinding : HistoryItemBinding) : ViewHolder(itemBinding.root){

        fun bind( transaction : TransactionDTO ){

            itemBinding.transactionItem = transaction
        }
    }
}