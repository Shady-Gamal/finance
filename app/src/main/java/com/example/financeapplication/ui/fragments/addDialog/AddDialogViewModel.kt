package com.example.financeapplication.ui.fragments.addDialog

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.DataUtils
import com.example.domain.models.Resource
import com.example.domain.useCases.AddRecipientUseCase
import com.example.financeapplication.ui.fragments.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddDialogViewModel @Inject constructor(
    private val addRecipientUseCase: AddRecipientUseCase
) : ViewModel() {

    val recipientId : MutableLiveData<String> = MutableLiveData("AbVV9puAtkXAoLSPgaVw1fsK8xo2")
    private val _uiState = MutableStateFlow(SaveRecipientState())
    val uiState: StateFlow<SaveRecipientState> = _uiState.asStateFlow()

  fun addRecipient(){


      viewModelScope.launch {
          addRecipientUseCase.invoke(recipientId.value!!).collect(){

              when (it){
                  is Resource.Success -> {
                      _uiState.update {currentUiState ->
                          currentUiState.copy(isSaved = true)
                      }
                  }

                  is Resource.Loading -> {
                      _uiState.update {currentUiState ->
                          currentUiState.copy(isLoading = true)
                      }
                  }

                  is Resource.Error ->{
                      _uiState.update {currentUiState ->
                          currentUiState.copy( error = it.message)
                      }
                  }
                  }
              }
          }


      }
  }
