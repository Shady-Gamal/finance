package com.example.financeapplication.di

import com.example.domain.repositories.AuthRepository
import com.example.domain.repositories.RecipientRepository
import com.example.domain.useCases.AddRecipientUseCase
import com.example.domain.useCases.FetchRecipientsUseCase
import com.example.domain.useCases.SignInWithEmailAndPasswordUseCase
import com.example.domain.useCases.SignUpWithEmailAndPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {


    @Singleton
    @Provides
    fun provideSingUpWithEmailAndPasswordUseCaseUseCase(authRepository: AuthRepository): SignUpWithEmailAndPasswordUseCase {
        return SignUpWithEmailAndPasswordUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideSingIpWithEmailAndPasswordUseCaseUseCase(authRepository: AuthRepository): SignInWithEmailAndPasswordUseCase {
        return SignInWithEmailAndPasswordUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideAddRecipientUseCase(recipientRepository: RecipientRepository) : AddRecipientUseCase{

        return AddRecipientUseCase(recipientRepository)
    }

    @Singleton
    @Provides
    fun provideFetchRecipientsUseCase(recipientRepository: RecipientRepository) : FetchRecipientsUseCase{

        return FetchRecipientsUseCase(recipientRepository)
    }
}