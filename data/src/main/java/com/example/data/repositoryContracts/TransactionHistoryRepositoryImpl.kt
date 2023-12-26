package com.example.data.repositoryContracts

import com.example.data.firestore.Constants
import com.example.domain.entities.DataUtils
import com.example.domain.entities.TransactionDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.TransactionHistoryRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionHistoryRepositoryImpl @Inject constructor(
    val firestore: FirebaseFirestore
) : TransactionHistoryRepository {
    override suspend fun addTransactionDetails(transaction: TransactionDTO): Flow<Resource<Boolean>> {

        return flow<Resource<Boolean>> {

           val doc = firestore
                .collection(Constants.History_Document_NAME)
                .document(DataUtils.user?.value?.id!!)
                .collection(Constants.History_Document_NAME)
                .document()
                transaction.id = doc.id

                 doc
                .set(transaction)
                .await()

            emit(Resource.Success(true))
        }.catch {
            emit(Resource.Error(it.message ?: "error"))
        }.onStart {

            emit(Resource.Loading())
        }
    }

    override suspend fun getTransactionHistory(id: String): Flow<Resource<List<TransactionDTO>>> {
        TODO("Not yet implemented")
    }
}