package com.example.financeapplication.ui.fragments.transfer

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.DataUtils
import com.example.domain.entities.FinanceDTO
import com.example.domain.entities.TransactionDTO
import com.example.domain.models.Resource
import com.example.domain.useCases.AddTransactionDetailsUseCase
import com.example.domain.useCases.GetFinanceDetailsUseCase
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
     savedStateHandle: SavedStateHandle,
    val transferFundsUserCase: TransferFundsUserCase,
    val getFinanceDetailsUseCase: GetFinanceDetailsUseCase,
    val addTransactionDetailsUseCase: AddTransactionDetailsUseCase

    ) : ViewModel() {


    private val _uiState = MutableStateFlow(TransferState())
    val uiState: StateFlow<TransferState> = _uiState.asStateFlow()
    var transferValue : String ?= null
    val receiverInfo= TransferFragmentArgs.fromSavedStateHandle(savedStateHandle).recipient

    private val _financeState = MutableStateFlow(FinanceDetailsState())
    val financeState: StateFlow<FinanceDetailsState> = _financeState.asStateFlow()


    init {
        getFinanceDetails()
    }



    fun transferFunds(){

        if(financeState.value.financeDetails?.balance!! >= transferValue?.toDouble()!! &&  transferValue != null){

            updateFirestoreBalance()
        }

    }
        fun updateFirestoreBalance(){

            viewModelScope.launch {
                transferFundsUserCase.invoke(receiverInfo?.recipientId!!, transferValue!!.toDouble()).collect(){
                    when (it){
                        is Resource.Success -> {
                            addTransactionToHistory()
                            _financeState.update {
                                it.copy(financeDetails = it.financeDetails?.copy(balance = it.financeDetails?.balance!!.minus(
                                    transferValue!!.toDouble()))
                                )

                            }
                            Log.e("ok", financeState.value.financeDetails.toString())
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

    fun getFinanceDetails(){

        viewModelScope.launch {
        getFinanceDetailsUseCase.invoke(DataUtils.user?.value?.id!!).collect(){

            when (it) {
                is Resource.Success -> {

                    _financeState.update { currentUiState ->
                        currentUiState.copy(financeDetails = it.data, isLoading = false)
                    }
                }

                is Resource.Loading -> _financeState.update { currentUiState ->
                    currentUiState.copy(isLoading = true)
                }

                is Resource.Error -> {
                    _financeState.update { currentUiState ->
                        currentUiState.copy(error = it.message, isLoading = false)

                    }
                }
            }
            }
        }
    }

    fun addTransactionToHistory(){
       val transaction = TransactionDTO(senderId = DataUtils.user?.value?.id,
           value = transferValue?.toDouble(),
           receiverId = receiverInfo?.recipientId,
           receiverName = receiverInfo?.recipientFullName,
           profilePic = receiverInfo?.recipientPhoto

           )
        viewModelScope.launch {
            addTransactionDetailsUseCase.invoke(transaction).collect(){

                when (it) {
                    is Resource.Success -> {

                        Log.e("TAG", "added: ", )

                    }

                    is Resource.Loading -> _financeState.update { currentUiState ->
                        currentUiState.copy(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.e("TAG", it.message.toString() )

                        }
                    }
                }

        }
        }

    }


