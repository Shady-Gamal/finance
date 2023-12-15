package com.example.financeapplication.di

import com.example.domain.repositories.UserRepository
import com.example.domain.repositories.RecipientRepository
import com.example.domain.repositories.StorageRepository
import com.example.domain.useCases.AddRecipientUseCase
import com.example.domain.useCases.FetchRecipientsUseCase
import com.example.domain.useCases.SignInWithEmailAndPasswordUseCase
import com.example.domain.useCases.SignOutUseCase
import com.example.domain.useCases.SignUpWithEmailAndPasswordUseCase
import com.example.domain.useCases.UpdateUserDataUseCase
import com.example.domain.useCases.UploadImageUseCase
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
    fun provideSingUpWithEmailAndPasswordUseCaseUseCase(userRepository: UserRepository): SignUpWithEmailAndPasswordUseCase {
        return SignUpWithEmailAndPasswordUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideSingIpWithEmailAndPasswordUseCaseUseCase(userRepository: UserRepository): SignInWithEmailAndPasswordUseCase {
        return SignInWithEmailAndPasswordUseCase(userRepository)
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


    @Singleton
    @Provides
    fun provideUploadImageUseCase(storageRepository: StorageRepository) : UploadImageUseCase{

        return UploadImageUseCase(storageRepository)
    }


    @Singleton
    @Provides
    fun provideSignOutUseCase(userRepository: UserRepository) : SignOutUseCase{

        return SignOutUseCase(userRepository)
    }


    @Singleton
    @Provides
    fun provideUpdateUserDataUserCase(userRepository: UserRepository) : UpdateUserDataUseCase{

        return UpdateUserDataUseCase(userRepository)
    }
}