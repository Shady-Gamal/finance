package com.example.financeapplication.ui.fragments.loading

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.financeapplication.R
import com.example.financeapplication.databinding.FragmentLoadingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class LoadingFragment : DialogFragment() {

    private lateinit var binding : FragmentLoadingBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = MaterialAlertDialogBuilder(it)
            binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.fragment_loading , null, false
            )

            builder.setBackground((ColorDrawable(Color.TRANSPARENT)))
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    }







