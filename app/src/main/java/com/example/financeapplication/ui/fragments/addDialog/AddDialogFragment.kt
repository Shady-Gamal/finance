package com.example.financeapplication.ui.fragments.addDialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.financeapplication.R
import com.example.financeapplication.databinding.FragmentAddDialogBinding
import com.example.financeapplication.ui.fragments.loading.LoadingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddDialogFragment : DialogFragment() {


    val viewModel : AddDialogViewModel by viewModels()
    lateinit var binding : FragmentAddDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        return activity?.let {

            val builder = AlertDialog.Builder(it)
            binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.fragment_add_dialog , null, false
            )
            binding.viewModel = this@AddDialogFragment.viewModel
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                    viewModel.uiState.collect { uiState ->
                        if (uiState.isSaved) {
                            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()

                           dismiss()
                        } else if (uiState.isLoading){
                            Log.e("TAG", "loading " )
                        }
                        else if (!(uiState.error.isNullOrBlank())){
                            Toast.makeText(requireContext(), uiState.error, Toast.LENGTH_SHORT).show()

                        }

                    }

            }
        }

        Log.e("gay", "called: ", )


    }


}