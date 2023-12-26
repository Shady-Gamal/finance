package com.example.data.di

import com.example.data.repositoryContracts.FinanceRepositoryImpl
import com.example.data.repositoryContracts.QrCodeRepositoryImpl
import com.example.data.repositoryContracts.UserRepositoryImpl
import com.example.data.repositoryContracts.RecipientRepositoryImpl
import com.example.data.repositoryContracts.StorageRepositoryImpl
import com.example.data.repositoryContracts.TransactionHistoryRepositoryImpl
import com.example.domain.repositories.FinanceRepository
import com.example.domain.repositories.QrCodeRepository
import com.example.domain.repositories.UserRepository
import com.example.domain.repositories.RecipientRepository
import com.example.domain.repositories.StorageRepository
import com.example.domain.repositories.TransactionHistoryRepository
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
    abstract fun bindAuthRepository(authRepositoryImpl: UserRepositoryImpl) : UserRepository
    @Binds
    @Singleton
   abstract fun bindRecipientRepository(recipientRepositoryImpl: RecipientRepositoryImpl) : RecipientRepository

    @Binds
    @Singleton
    abstract fun bindStorageRepository(storageRepositoryImpl: StorageRepositoryImpl) : StorageRepository

    @Binds
    @Singleton
    abstract fun bindFinanceRepository(financeRepositoryImpl: FinanceRepositoryImpl) : FinanceRepository


    @Binds
    @Singleton
    abstract fun bindTransactionHistoryRepository(transactionHistoryRepositoryImpl: TransactionHistoryRepositoryImpl) : TransactionHistoryRepository


    @Binds
    @Singleton
    abstract fun bindQrCodeRepository(qrCodeRepositoryImpl: QrCodeRepositoryImpl) : QrCodeRepository


}