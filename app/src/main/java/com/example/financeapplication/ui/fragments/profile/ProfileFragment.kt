package com.example.financeapplication.ui.fragments.profile

import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.entities.DataUtils
import com.example.financeapplication.R
import com.example.financeapplication.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.FileDescriptor
import java.io.FileInputStream
import java.net.URI


@AndroidEntryPoint
class ProfileFragment : Fragment() {

     val viewModel: ProfileViewModel by viewModels()
     lateinit var binding : FragmentProfileBinding
     lateinit var fileDescriptor : ParcelFileDescriptor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dataUtils = DataUtils
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                fileDescriptor = requireContext().contentResolver.openFileDescriptor(uri,"r")!!
                val fileInputStream = FileInputStream(fileDescriptor.fileDescriptor)
                viewModel.uploadPhoto(fileInputStream)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)  {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isUploaded) {

                        Toast.makeText(requireContext(), "Photo updated", Toast.LENGTH_SHORT).show()
                        fileDescriptor.close()


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
        binding.changeProfilePicText.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }




    }



}