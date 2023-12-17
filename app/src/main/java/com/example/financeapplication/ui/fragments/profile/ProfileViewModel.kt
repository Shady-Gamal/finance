package com.example.financeapplication.ui.fragments.profile

import android.os.Build
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.AppUserDTO
import com.example.domain.entities.DataUtils
import com.example.domain.models.Resource
import com.example.domain.useCases.UpdateUserDataUseCase
import com.example.domain.useCases.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.FileInputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UploadState())
    val uiState: StateFlow<UploadState> = _uiState.asStateFlow()
    private val _updateState = MutableStateFlow(UpdateState())
    val updateState: StateFlow<UpdateState> = _updateState.asStateFlow()
    var profileInfo: AppUserDTO = DataUtils.user!!.value?.copy()!!



    fun uploadPhoto(fileInputStream : FileInputStream){

        viewModelScope.launch {
            uploadImageUseCase.invoke(fileInputStream).collect(){
                when (it){
                    is Resource.Success -> {

                        _uiState.update {currentUiState ->
                            currentUiState.copy( isUploaded = true)
                        }

                    }
                    is Resource.Error -> {
                        _uiState.update {currentUiState ->
                            currentUiState.copy( error = it.message)
                        }
                    }
                    is Resource.Loading -> {

                        _uiState.update {currentUiState ->
                            currentUiState.copy( isLoading = true,
                                isUploaded = false)
                        }
                    }
                }
            }
        }


    }

    fun updateUserData() {
        viewModelScope.launch {
            updateUserDataUseCase.invoke(profileInfo).collect() {

                when (it){
                    is Resource.Success -> {
                        _updateState.update {currentUiState ->
                            DataUtils.user?.value = profileInfo.copy(
                                profilePictureUrl = DataUtils.user?.value?.profilePictureUrl
                            )
                            currentUiState.copy( isUpdated = true)
                        }
                    }
                    is Resource.Error -> {
                        _updateState.update {currentUiState ->
                            currentUiState.copy( error = it.message)
                        }
                    }
                    is Resource.Loading -> {

                        _updateState.update {currentUiState ->
                            currentUiState.copy( isLoading = true,
                               )
                        }
                    }
                }


            }


        }

    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun handleInputLayout(inputLayout : View, editText: EditText, originalValue : String){

        if (!(inputLayout.isActivated)) {
            inputLayout.isActivated = true

            editText.focusable = View.FOCUSABLE
            editText.isFocusableInTouchMode = true
            editText.requestFocus()
            editText.setSelection(editText.length())
            editText.windowInsetsController?.show(WindowInsetsCompat.Type.ime())
        }
        else {


            editText.setText(originalValue)
            editText.setSelection(originalValue.length)
        }
    }

}