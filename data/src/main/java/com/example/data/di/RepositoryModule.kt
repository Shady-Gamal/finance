package com.example.data.di

import com.example.data.repositoryContracts.AuthRepositoryImpl
import com.example.data.repositoryContracts.RecipientRepositoryImpl
import com.example.domain.repositories.AuthRepository
import com.example.domain.repositories.RecipientRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository
    @Binds
    @Singleton
   abstract fun bindRecipientRepository(recipientRepositoryImpl: RecipientRepositoryImpl) : RecipientRepository

}