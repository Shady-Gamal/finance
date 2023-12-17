package com.example.financeapplication.ui.fragments.profile

import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.domain.entities.DataUtils
import com.example.financeapplication.R
import com.example.financeapplication.databinding.FragmentProfileBinding
import com.example.financeapplication.ui.activities.homeActivity.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.FileInputStream


@AndroidEntryPoint
class ProfileFragment : Fragment() {

     val viewModel: ProfileViewModel by viewModels()
     lateinit var binding : FragmentProfileBinding
     lateinit var fileDescriptor : ParcelFileDescriptor
    private val activityViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = this@ProfileFragment.viewModel

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                fileDescriptor = requireContext().contentResolver.openFileDescriptor(uri,"r")!!
                val fileInputStream = FileInputStream(fileDescriptor.fileDescriptor)
                viewModel.uploadPhoto(fileInputStream)
                Glide.with(this@ProfileFragment).load(uri)
                    .into(binding.profileImage)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)  {

                //val callback = requireActivity().onBackPressedDispatcher
                viewModel.uiState.collect { uiState ->
                    if (uiState.isUploaded) {
                        Toast.makeText(requireContext(), "Photo updated", Toast.LENGTH_SHORT).show()
                        Glide.with(this@ProfileFragment).load(DataUtils.user?.value?.profilePictureUrl).into(binding.profileImage)
                        activityViewModel.refreshDrawerHeader(uiState.isUploaded)
                        fileDescriptor.close()
                        binding.progressCircular.visibility = View.GONE
                        binding.profileImage.clearColorFilter()
                        binding.saveButton.isClickable = true
                        binding.saveButton.text = "Save Changes"
                    } else if (uiState.isLoading){
                        binding.progressCircular.visibility = View.VISIBLE
                        binding.profileImage.setColorFilter(R.color.black_transparent)
                        binding.saveButton.isClickable = false
                        binding.saveButton.text = "Loading"
                    }
                    else if (!(uiState.error.isNullOrBlank())){
                        Toast.makeText(requireContext(), uiState.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)  {
                viewModel.updateState.collect { updateState ->
                    if (updateState.isUpdated) {
                        activityViewModel.refreshDrawerHeader(updateState.isUpdated)
                        findNavController().popBackStack()
                    } else if (updateState.isLoading){
                        // LoadingFragment().show(
                        //  childFragmentManager, "loading_fragment")
                    }
                    else if (!(updateState.error.isNullOrBlank())){
                        Toast.makeText(requireContext(), updateState.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.changeProfilePicText.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }


        binding.editFullNameLayout.setEndIconOnClickListener {

            viewModel.handleInputLayout(it, binding.editFullNameText, DataUtils.user?.value?.fullName!!)
        }


        binding.editEmailLayout.setEndIconOnClickListener {

            viewModel.handleInputLayout(it, binding.editEmailText, DataUtils.user?.value?.email!!)
        }

        binding.editPasswordLayout.setEndIconOnClickListener {

           // viewModel.handleInputLayout(it, binding.editFullNameText, DataUtils.user?.fullName!!)
        }
        binding.editPhoneNumberLayout.setEndIconOnClickListener {

            //viewModel.handleInputLayout(it, binding.editFullNameText, DataUtils.user?.fullName!!)
        }

        binding.editAddressLayout.setEndIconOnClickListener {

            viewModel.handleInputLayout(it, binding.editAddressText, DataUtils.user?.value?.address ?: "")
        }

        binding.editOptionalAddressText.setOnClickListener {

            viewModel.handleInputLayout(it, binding.editOptionalAddressText, DataUtils.user?.value?.optionalAddress ?: "")
        }

        binding.editCountryLayout.setEndIconOnClickListener {

            viewModel.handleInputLayout(it, binding.editCountryText, DataUtils.user?.value?.optionalAddress ?: "")
        }

        binding.editZipCodeLayout.setEndIconOnClickListener {

            viewModel.handleInputLayout(it, binding.editZipCodeText, DataUtils.user?.value?.optionalAddress ?: "")
        }

    }

}
