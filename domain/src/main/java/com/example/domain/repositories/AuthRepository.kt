package com.example.domain.repositories

import com.example.domain.entities.AppUserDTO
import com.example.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    //fun isUserAuthenticatedInFirebase(): Boolean

    suspend fun signInWithEmailAndPassword(email: String, password:String): Flow<Resource<AppUserDTO?>>

    suspend fun singUpWithEmailAndPassword (email: String, password: String, user : AppUserDTO) : Flow<Resource<Boolean>>

    suspend fun signOut(): Flow<Resource<Boolean>>

    //fun getFirebaseAuthState(): Flow<Boolean>
}