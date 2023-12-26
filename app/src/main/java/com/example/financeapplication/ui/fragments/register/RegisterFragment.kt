package com.example.financeapplication.ui.fragments.register

import androidx.lifecycle.ViewModelProvider
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
import androidx.navigation.fragment.findNavController
import com.example.financeapplication.R
import com.example.financeapplication.databinding.FragmentRegisterBinding
import com.example.financeapplication.ui.fragments.loading.LoadingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private val viewModel: RegisterViewModel by viewModels()
    lateinit var binding : FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.viewModel = this@RegisterFragment.viewModel
            val loadingFragment = LoadingFragment()
        binding.loginText.setOnClickListener{
            findNavController().popBackStack()
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)  {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isRegistered) {
                        findNavController().popBackStack()
                    } else if (uiState.isLoading){
                        loadingFragment.show(
                            childFragmentManager, "loading_fragment")
                    }
                    else if (uiState.error != null){
                        Toast.makeText(requireContext(), uiState.error, Toast.LENGTH_SHORT).show()
                        loadingFragment.dismiss()
                    }

                }
            }
        }

    }

}