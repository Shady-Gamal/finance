package com.example.financeapplication.ui.fragments.recipients

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.financeapplication.R
import com.example.financeapplication.databinding.FragmentRecipientsBinding
import com.example.financeapplication.ui.fragments.addDialog.AddDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipientsFragment : Fragment() {



    lateinit var binding: FragmentRecipientsBinding
    val viewModel: RecipientsViewModel by viewModels()
    lateinit var recipientsAdapter: RecipientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_recipients,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recipientsAdapter = RecipientsAdapter(null)
        binding.recipientsRecyclerView.adapter = recipientsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)  {
                viewModel.uiState.collect { uiState ->
                    if (!(uiState.recipientsInfo.isNullOrEmpty())) {
                        recipientsAdapter.updateData(uiState.recipientsInfo)
                        Log.e("somanyquestions", uiState.recipientsInfo?.get(0)?.recipientFullName ?: "ok" )
                    } else if (uiState.isLoading){

                       // LoadingFragment().show(
                          //  childFragmentManager, "loading_fragment")
                    }
                    else if (!(uiState.error.isNullOrBlank())){
                        Toast.makeText(requireContext(), uiState.error, Toast.LENGTH_SHORT).show()

                    }

                }
            }
        }






        binding.addButton.setOnClickListener {

            AddDialogFragment().show(childFragmentManager,"homeboi")
        }
    }




}