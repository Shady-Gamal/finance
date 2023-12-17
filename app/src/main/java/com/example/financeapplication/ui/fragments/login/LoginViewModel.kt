package com.example.financeapplication.ui.fragments.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.DataUtils
import com.example.domain.models.Resource
import com.example.domain.useCases.SignInWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase
) : ViewModel(){

    var email : String ?= null
    var password : String ?= null
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()


    fun signInWithEmailAndPassword(){

        viewModelScope.launch {
            signInWithEmailAndPasswordUseCase.invoke(email!!, password!!).collect() {
        when (it){
            is Resource.Success -> {
                DataUtils.user?.value = it.data
                _uiState.update {currentUiState ->
                    currentUiState.copy( isLoggedIn = true, isLoading = false)
                }
            }
            is Resource.Loading -> _uiState.update {currentUiState ->
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