package com.example.financeapplication.ui.fragments.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.AppUserDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.AuthRepository
import com.example.domain.useCases.SignUpWithEmailAndPasswordUseCase
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
class RegisterViewModel @Inject constructor(
    val signUpWithEmailAndPasswordUseCase: SignUpWithEmailAndPasswordUseCase
) : ViewModel() {

    var name : MutableLiveData<String> = MutableLiveData()
    var email : MutableLiveData<String> = MutableLiveData()
    var password : MutableLiveData<String> = MutableLiveData()
    var passward2 : MutableLiveData<String> = MutableLiveData()

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()





    fun createAccountOnFirebase(){

        val user = AppUserDTO(null,name.value,email.value)
        viewModelScope.launch {
            signUpWithEmailAndPasswordUseCase(email.value!!, password.value!!, user).collect(){
                when (it){

                    is Resource.Success -> {
                        _uiState.update {currentUiState ->
                            currentUiState.copy( isRegistered = true, isLoading = false)
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