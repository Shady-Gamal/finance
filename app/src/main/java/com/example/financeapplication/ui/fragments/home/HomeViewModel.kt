package com.example.financeapplication.ui.fragments.home

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.AppUserDTO
import com.example.domain.entities.DataUtils
import com.example.domain.entities.FinanceDTO
import com.example.domain.models.Resource
import com.example.domain.useCases.GetFinanceDetailsUseCase
import com.example.domain.useCases.GetCurrentUserIdUseCase
import com.example.domain.useCases.GetRecentTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val getFinanceDetailsUseCase: GetFinanceDetailsUseCase,
    getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    val getRecentTransactionsUseCase: GetRecentTransactionsUseCase

) : ViewModel() {


    private val _uiState = MutableStateFlow(FinanceDetailsState())
    val uiState: StateFlow<FinanceDetailsState> = _uiState.asStateFlow()

    private val _recentTransactionsState = MutableStateFlow(RecentTransactionsState())
    val recentTransactionsState: StateFlow<RecentTransactionsState> = _recentTransactionsState.asStateFlow()
    val currentUserIdUseCase = getCurrentUserIdUseCase.invoke()



    fun getFinanceDetails(){

        viewModelScope.launch {


            if (currentUserIdUseCase != null) {
                getFinanceDetailsUseCase.invoke(currentUserIdUseCase).collect(){

                    when (it){
                        is Resource.Success -> {

                            _uiState.update {currentUiState ->
                                currentUiState.copy( financeDetails = it.data, isLoading = false)
                            }
                        }

                        is Resource.Loading -> _uiState.update { currentUiState ->
                            currentUiState.copy( isLoading = true)
                        }

                        is Resource.Error -> {
                            _uiState.update { currentUiState ->
                                currentUiState.copy(error = it.message, isLoading = false)

                            }
                        }
                    }
                }
            }
        }


    }

    fun getRecentTransactions(){

        viewModelScope.launch {


            if (currentUserIdUseCase != null) {
                getRecentTransactionsUseCase.invoke(currentUserIdUseCase).collect(){

                    when (it){
                        is Resource.Success -> {

                            _recentTransactionsState.update {currentUiState ->
                                currentUiState.copy( recentTransactions = it.data, isLoading = false)

                            }
                            Log.e("TAG", it.data.toString())
                        }

                        is Resource.Loading -> _recentTransactionsState.update { currentUiState ->
                            currentUiState.copy( isLoading = true)
                        }

                        is Resource.Error -> {
                            _recentTransactionsState.update { currentUiState ->
                                currentUiState.copy(error = it.message, isLoading = false)

                            }
                            Log.e("TAG", it.message.toString())
                        }
                    }
                }
            }
        }


    }






}