package com.example.financeapplication.ui.fragments.addDialog

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.DataUtils
import com.example.domain.models.Resource
import com.example.domain.useCases.AddRecipientUseCase
import com.example.domain.useCases.ScanQrCodeUseCase
import com.example.domain.useCases.ShowQrCodeUSeCase
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
    private val addRecipientUseCase: AddRecipientUseCase,
    val scanQrCodeUseCase: ScanQrCodeUseCase,
    val showQrCodeUSeCase: ShowQrCodeUSeCase
) : ViewModel() {

    val recipientId : MutableStateFlow<String?> = MutableStateFlow("")
    private val _uiState = MutableStateFlow(SaveRecipientState())
    val uiState: StateFlow<SaveRecipientState> = _uiState.asStateFlow()

    private val _qrCodeScanState = MutableStateFlow(QrCodeScanState())
    val qrCodeScanState: StateFlow<QrCodeScanState> = _qrCodeScanState.asStateFlow()

    var qrCodeImage : Bitmap ?= null



    init {
        showQrCode()
    }
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

    fun scanQrCode(){

        viewModelScope.launch {
            scanQrCodeUseCase.invoke().collect(){

            when (it){

                is Resource.Success ->{

                    recipientId.value = it.data!!


                    _qrCodeScanState.update { currentUiState ->
                        currentUiState.copy( isScanned = true)
                    }
                    addRecipient()
                }

                is Resource.Error -> {
                    _qrCodeScanState.update { currentUiState ->
                        currentUiState.copy( error = it.message)
                    }
                }
                is Resource.Loading -> {
                    _qrCodeScanState.update { currentUiState ->
                        currentUiState.copy( isLoading = true)
                    }
                }
            }


            }
        }
    }

    fun showQrCode(){

        viewModelScope.launch {

            showQrCodeUSeCase.invoke(DataUtils.user?.value?.id!!).collect(){

                when (it){
                    is Resource.Success -> {
                      qrCodeImage = it.data
                    }
                    is Resource.Error -> {
                        Log.e("money", it.message.toString() )

                    }


                        is Resource.Loading ->{

                        }


                    }}
        }
    }
  }
