package com.example.data.repositoryContracts

import android.util.Log
import com.example.data.firestore.Constants
import com.example.data.model.Transaction
import com.example.data.model.toTransactionDTO
import com.example.domain.entities.DataUtils
import com.example.domain.entities.TransactionDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.TransactionHistoryRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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

           val senderDoc = firestore
                .collection(Constants.History_Collection_NAME)
                .document(DataUtils.user?.value?.id!!)
                .collection(Constants.History_Collection_NAME)
                .document()
             val transactionId = senderDoc.id

            transaction.id = transactionId

                senderDoc
                .set(transaction)
                .await()

               val receiverDoc = firestore
                .collection(Constants.History_Collection_NAME)
                .document(transaction.receiverId!!)
                .collection(Constants.History_Collection_NAME)
                .document(transactionId)

                transaction.isSender = false
                receiverDoc
                .set(transaction)
                .await()



            emit(Resource.Success(true))
        }.catch {
            emit(Resource.Error(it.message ?: "error"))

        }.onStart {

            emit(Resource.Loading())
        }
    }

    override suspend fun getSentTransactionHistory(id: String): Flow<Resource<List<TransactionDTO>>> {
        return flow<Resource<List<TransactionDTO>>> {
            val transactionHistory = firestore
                .collection(Constants.History_Collection_NAME)
                .document(DataUtils.user?.value?.id!!)
                .collection(Constants.History_Collection_NAME)
                .whereEqualTo("sender", true)
                .get()
                .await()
            val history = transactionHistory.toObjects(Transaction::class.java).toList()


         emit(Resource.Success(history.map {
             it.toTransactionDTO() }
         ))
        }.catch {
            emit(Resource.Error(it.message ?: " error"))
        }.onStart {
            emit(Resource.Loading())
        }



    }
    override suspend fun getReceivedTransactionHistory(id: String): Flow<Resource<List<TransactionDTO>>> {
        return flow<Resource<List<TransactionDTO>>> {
            val transactionHistory = firestore
                .collection(Constants.History_Collection_NAME)
                .document(DataUtils.user?.value?.id!!)
                .collection(Constants.History_Collection_NAME)
                .whereEqualTo("sender", false)
                .get()
                .await()
            val history = transactionHistory.toObjects(Transaction::class.java).toList()


            emit(Resource.Success(history.map {
                it.toTransactionDTO() }
            ))
        }.catch {
            emit(Resource.Error(it.message ?: " error"))
        }.onStart {
            emit(Resource.Loading())
        }



    }

    override suspend fun getRecentTransactions(id: String): Flow<Resource<List<TransactionDTO>>> {
       return flow<Resource<List<TransactionDTO>>>{
          val history =  firestore
               .collection(Constants.History_Collection_NAME)
               .document(id)
               .collection(Constants.History_Collection_NAME)
               .orderBy("date", Query.Direction.DESCENDING)
               .limit(4)
                .get()
               .await()

           val historyList = history.toObjects(Transaction::class.java).toList()



           emit(Resource.Success(historyList.map {
               it.toTransactionDTO()
           }))


       }.catch {

       }.onStart {
           emit(Resource.Loading())
       }

    }
}