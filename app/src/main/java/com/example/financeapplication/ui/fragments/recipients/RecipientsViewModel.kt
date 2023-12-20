package com.example.financeapplication.ui.fragments.recipients

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Resource
import com.example.domain.useCases.FetchRecipientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipientsViewModel @Inject constructor(
    val fetchRecipientsUseCase: FetchRecipientsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipientState())
    val uiState: StateFlow<RecipientState> = _uiState.asStateFlow()


    init {
        getAllRecipients()
    }
    fun getAllRecipients(){

        viewModelScope.launch {
            fetchRecipientsUseCase.invoke().collect(){

              when (it){

                  is Resource.Success -> {
                      _uiState.update {currentUiState ->
                          currentUiState.copy( recipientsInfo = it.data)
                      }



                  }
                  is Resource.Error -> {
                      _uiState.update {currentUiState ->
                          currentUiState.copy( error = it.message)
                      }
                  }
                  is Resource.Loading -> {

                      _uiState.update {currentUiState ->
                          currentUiState.copy( isLoading = true)
                      }
                  }





              }



            }
        }


    }
}