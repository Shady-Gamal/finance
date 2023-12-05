package com.example.financeapplication.ui.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Resource
import com.example.domain.useCases.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
   val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UploadState())
    val uiState: StateFlow<UploadState> = _uiState.asStateFlow()


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


}