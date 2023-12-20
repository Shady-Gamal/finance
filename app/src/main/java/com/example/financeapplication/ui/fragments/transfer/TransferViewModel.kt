package com.example.financeapplication.ui.fragments.transfer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Resource
import com.example.domain.useCases.TransferFundsUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val transferFundsUserCase: TransferFundsUserCase

    ) : ViewModel() {


    private val _uiState = MutableStateFlow(TransferState())
    val uiState: StateFlow<TransferState> = _uiState.asStateFlow()
    var transferValue : String ?= null
    val receiverId = TransferFragmentArgs.fromSavedStateHandle(savedStateHandle).recipient?.recipientId!!


        fun transferFunds(){

            viewModelScope.launch {
                transferFundsUserCase.invoke(receiverId, transferValue!!.toDouble()).collect(){
                    when (it){
                        is Resource.Success -> {
                            _uiState.update {currentUiState ->
                                currentUiState.copy( isTransferred = true, isLoading = false)
                            }
                        }
                        is Resource.Loading->{
                            _uiState.update {currentUiState ->
                                currentUiState.copy( isLoading = true)}
                        }
                        is Resource.Error -> {
                            _uiState.update {currentUiState ->
                                currentUiState.copy( error = it.message, isLoading = false)}
                        }

                    }
                }
            }
        }
}