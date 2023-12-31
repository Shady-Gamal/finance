package com.example.financeapplication.ui.fragments.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.DataUtils
import com.example.domain.models.Resource
import com.example.domain.useCases.GetReceivedTransactionHistoryUseCase
import com.example.domain.useCases.GetSentTransactionHistoryUseCase
import com.example.financeapplication.ui.fragments.home.RecentTransactionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    val getReceivedTransactionHistoryUseCase: GetReceivedTransactionHistoryUseCase,
    val getSentTransactionHistoryUseCase: GetSentTransactionHistoryUseCase


) : ViewModel() {


    private val _historyState = MutableStateFlow(HistoryState())
    val historyState: StateFlow<HistoryState> = _historyState.asStateFlow()

    init {
        getSentHistory()
    }
    fun getSentHistory(){

        viewModelScope.launch {
            getSentTransactionHistoryUseCase.invoke(DataUtils.user?.value?.id!!).collect(){

                when (it){
                    is Resource.Success -> {

                        _historyState.update {currentUiState ->
                            currentUiState.copy( history = it.data, isLoading = false)

                        }

                    }

                    is Resource.Loading -> _historyState.update { currentUiState ->
                        currentUiState.copy( isLoading = true)
                    }

                    is Resource.Error -> {
                        _historyState.update { currentUiState ->
                            currentUiState.copy(error = it.message, isLoading = false)

                        }

                    }
                }


            }

        }

    }

        fun getReceivedHistory(){
        viewModelScope.launch {
            getReceivedTransactionHistoryUseCase.invoke(DataUtils.user?.value?.id!!).collect() {

                when (it) {
                    is Resource.Success -> {

                        _historyState.update { currentUiState ->
                            currentUiState.copy(history = it.data, isLoading = false)

                        }

                    }

                    is Resource.Loading -> _historyState.update { currentUiState ->
                        currentUiState.copy(isLoading = true)
                    }

                    is Resource.Error -> {
                        _historyState.update { currentUiState ->
                            currentUiState.copy(error = it.message, isLoading = false)

                        }

                    }
                }
            }

            }

        }


    }
