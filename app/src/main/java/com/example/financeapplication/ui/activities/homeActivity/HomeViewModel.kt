package com.example.financeapplication.ui.activities.homeActivity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.DataUtils
import com.example.domain.models.Resource
import com.example.domain.useCases.GetCurrentUserIdUseCase
import com.example.domain.useCases.SignOutUseCase
import com.example.domain.useCases.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val signOutUseCase: SignOutUseCase,
    getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    val getUserDataUseCase: GetUserDataUseCase,


    ) : ViewModel() {

    private val _logoutState = MutableStateFlow(LogoutState())
    val logoutState: StateFlow<LogoutState> = _logoutState.asStateFlow()

    private val _refreshDrawerHeader = Channel<Boolean>()
    val refreshDrawerHeader = _refreshDrawerHeader.receiveAsFlow()

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()


    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()


    val currentUserId = getCurrentUserIdUseCase.invoke()

    init { handleAuthentication() }


    private fun handleAuthentication(){


        if (currentUserId.isNullOrBlank()) {
                _userState.update { currentUiState ->
                    currentUiState.copy(isAuthenticated = false)
                }
                _isReady.value = true


            }else if (currentUserId != null ) {
                _isReady.value = true

                DataUtils.user?.value?.id = currentUserId

                getUserData()

            }





    }

    fun getUserData(){


        viewModelScope.launch {
            getUserDataUseCase.invoke(currentUserId!!).collect() {
                when (it) {
                    is Resource.Success -> {
                        DataUtils.user?.value = it.data

                        _userState.update { currentUiState ->
                            currentUiState.copy(isDataLoaded = it.data,)
                        }
                    }
                    is Resource.Error -> {
                        _userState.update { currentUiState ->
                            currentUiState.copy(error = it.message)

                        }
                    }

                    is Resource.Loading ->{
                        _userState.update { currentUiState ->
                            currentUiState.copy(isLoading = true)

                        }

                    }
                }


            }
        }

    }


    fun signOut(){
        viewModelScope.launch {
            signOutUseCase.invoke().collect() {

                when (it){
                        is Resource.Success -> {
                            Log.e("TAG", "signOut: ", )
                            _logoutState.update { currentUiState ->
                                currentUiState.copy( isLoggedOut = true, isLoading = false)
                            }

                        }
                        is Resource.Loading -> _logoutState.update { currentUiState ->
                            currentUiState.copy( isLoading = true)
                        }
                        is Resource.Error -> {
                            Log.e("TAG", "signOut: ", )
                            _logoutState.update { currentUiState ->
                                currentUiState.copy(error = it.message, isLoading = false)

                            }
                    }
                }

            }

        }
    }

    fun refreshDrawerHeader(it:Boolean){

        viewModelScope.launch {
            _refreshDrawerHeader.send(it)
        }
    }

}