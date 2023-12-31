package com.example.financeapplication.ui.fragments.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.financeapplication.R
import com.example.financeapplication.databinding.FragmentHistoryBinding
import com.example.financeapplication.ui.fragments.common.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HistoryFragment : Fragment() {

    lateinit var binding : FragmentHistoryBinding
    lateinit var historyAdapter: HistoryAdapter
    val viewModel : HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history ,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyAdapter = HistoryAdapter(null)
        binding.historyRecyclerView.adapter = historyAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)  {

                viewModel.historyState.collect{uiState ->

                    if (uiState.history != null){

                        historyAdapter.updateData(uiState.history)


                    }

                }


            }}



        binding.togglebuttongroup.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->

            if (!isChecked) return@addOnButtonCheckedListener


           when (checkedId){


               binding.receivedButton.id -> {
                   viewModel.getReceivedHistory()
                   Log.e("kayne1", "onViewCreated: ", )
               }

               binding.transferredButton.id ->{

                   viewModel.getSentHistory()
                   Log.e("kayne2", "onViewCreated: ", )
               }
           }
        }

    }


}