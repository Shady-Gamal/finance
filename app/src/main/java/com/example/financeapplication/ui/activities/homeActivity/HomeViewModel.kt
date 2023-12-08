package com.example.financeapplication.ui.activities.homeActivity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.DataUtils
import com.example.domain.models.Resource
import com.example.domain.useCases.SignOutUseCase
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
class HomeViewModel @Inject constructor(
    val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogoutState())
    val uiState: StateFlow<LogoutState> = _uiState.asStateFlow()

    fun signOut(){
        viewModelScope.launch {
            signOutUseCase.invoke().collect() {

                when (it){
                        is Resource.Success -> {
                            _uiState.update {currentUiState ->
                                currentUiState.copy( isLoggedOut = true, isLoading = false)
                            }
                            Log.e("TAG", "yo")
                            DataUtils.user = null

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