package com.example.financeapplication.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.domain.entities.DataUtils
import com.example.financeapplication.R
import com.example.financeapplication.databinding.FragmentHomeBinding
import com.example.financeapplication.ui.fragments.common.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    val viewModel : HomeViewModel by viewModels()
    lateinit var historyAdapter: HistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home ,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dataUtils = DataUtils
        binding.viewModel = this@HomeFragment.viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        historyAdapter = HistoryAdapter(null)
        binding.recentTransactionsRecyclerview.adapter = historyAdapter



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)  {

                viewModel.recentTransactionsState.collect{uiState ->

                    if (uiState.recentTransactions != null){

                        historyAdapter.updateData(uiState.recentTransactions)


                    }

                }


            }}


        binding.RecipientsButton.setOnClickListener {

            findNavController().navigate(R.id.recipientsFragment)
        }

        binding.transferButton.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)

        }

    }

    override fun onStart() {
        super.onStart()

        viewModel.getFinanceDetails()
        viewModel.getRecentTransactions()
    }


}