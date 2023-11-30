package com.example.financeapplication.ui.fragments.recipients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.entities.RecipientDTO
import com.example.financeapplication.R
import com.example.financeapplication.databinding.RecipientItemBinding

class RecipientsAdapter(var recipients : List<RecipientDTO?>? = null) : RecyclerView.Adapter<RecipientsAdapter.RecipientViewHolder>() {



    fun updateData(recipients: List<RecipientDTO?>?) {
        this.recipients = recipients
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipientViewHolder {
        val itemBinding : RecipientItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recipient_item,
            parent,
            false
            )
        return RecipientViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return recipients?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecipientViewHolder, position: Int) {
        val item = recipients?.get(position)
        holder.bind(item)
    }


    class RecipientViewHolder(val itemBinding : RecipientItemBinding) : ViewHolder(itemBinding.root){

        fun bind(recipient:RecipientDTO?){

            itemBinding.recipientItem = recipient
        }

    }


}