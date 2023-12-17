package com.example.financeapplication.ui.activities.homeActivity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.DataUtils
import com.example.domain.models.Resource
import com.example.domain.useCases.IsUserAuthenticatedUseCase
import com.example.domain.useCases.SignOutUseCase
import com.example.domain.useCases.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
    val isUserAuthenticatedUseCase: IsUserAuthenticatedUseCase,
    val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {

    private val _logoutState = MutableStateFlow(LogoutState())
    val logoutState: StateFlow<LogoutState> = _logoutState.asStateFlow()

    private val _refreshDrawerHeader = Channel<Boolean>()
    val refreshDrawerHeader = _refreshDrawerHeader.receiveAsFlow()

    private val _userDataState = MutableStateFlow(UserDataState())
    val userDataState: StateFlow<UserDataState> = _userDataState.asStateFlow()


    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()


    val isAuthenticated = isUserAuthenticatedUseCase.invoke()


    init {

        viewModelScope.launch {
            if (isAuthenticated.isNullOrBlank()) {
                _userDataState.update { currentUiState ->
                    currentUiState.copy(doesntexist = true)
                }
                _isReady.value = true

                return@launch
            }else if (DataUtils.user == null) {
                Log.e("when data utils is null", DataUtils.user.toString() )
                getUserData()
                return@launch
            }

            _isReady.value = true

            }


    }

    fun getUserData(){

        viewModelScope.launch {
            getUserDataUseCase.invoke(isAuthenticated!!).collect() {
                when (it) {
                    is Resource.Success -> {
                        DataUtils.user = it.data
                        _isReady.value = true

                        _userDataState.update { currentUiState ->
                            currentUiState.copy(isLoaded = it.data,)
                        }

                    }

                    is Resource.Error -> {
                        _userDataState.update { currentUiState ->
                            currentUiState.copy(error = it.message)

                        }
                    }

                    is Resource.Loading ->{
                        _userDataState.update { currentUiState ->
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

                            DataUtils.user = null

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