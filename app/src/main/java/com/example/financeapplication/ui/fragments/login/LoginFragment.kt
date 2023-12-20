package com.example.financeapplication.ui.fragments.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
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
import com.example.domain.entities.DataUtils
import com.example.domain.models.Resource
import com.example.financeapplication.R
import com.example.financeapplication.databinding.FragmentLoginBinding
import com.example.financeapplication.ui.fragments.loading.LoadingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {



    val viewModel: LoginViewModel by viewModels()
    lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.viewModel = this@LoginFragment.viewModel
        binding.registerText.setOnClickListener{
            findNavController().navigate(R.id.registerFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)  {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isLoggedIn) {
                        findNavController().navigate(R.id.action_loginFragment_to_home_activity)
                        requireActivity().finish()


                        (parentFragmentManager.findFragmentByTag("Fragment") as? DialogFragment)?.dismiss()



                    } else if (uiState.isLoading){
                        LoadingFragment().show(
                            childFragmentManager, "Fragment"
                        )
                    }
                    else if (!(uiState.error.isNullOrBlank())){
                        Toast.makeText(requireContext(), uiState.error, Toast.LENGTH_SHORT).show()

                    }

                    }
        }
        }

    }


}