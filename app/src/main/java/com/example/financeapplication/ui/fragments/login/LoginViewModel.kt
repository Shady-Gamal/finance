package com.example.financeapplication.ui.fragments.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.DataUtils.user
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

    var email : MutableLiveData<String> = MutableLiveData()
    var password : MutableLiveData<String> = MutableLiveData()
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()


    fun signInWithEmailAndPassword(){

        viewModelScope.launch {
            signInWithEmailAndPasswordUseCase.invoke(email.value!!, password.value!!).collect() {
        when (it){
            is Resource.Success -> {
                user = it.data
                _uiState.update {currentUiState ->
                    currentUiState.copy( isLoggedIn = true, isLoading = false)
                }
                Log.e("throne2", it.data?.fullName.toString())
            }
            is Resource.Loading -> _uiState.update {currentUiState ->
                currentUiState.copy( isLoading = true)
            }
            is Resource.Error -> _uiState.value.error = it.message




        }

            }
        }

    }


}