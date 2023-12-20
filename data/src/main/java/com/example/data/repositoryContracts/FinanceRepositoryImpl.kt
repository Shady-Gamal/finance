package com.example.data.repositoryContracts

import com.example.data.firestore.Constants
import com.example.data.model.AppUser
import com.example.data.model.Finance
import com.example.data.model.toFinanceDTO
import com.example.domain.entities.DataUtils
import com.example.domain.entities.FinanceDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.FinanceRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor(
    val firestore: FirebaseFirestore
) : FinanceRepository {
    override suspend fun getFinanceDetails( id : String): Flow<Resource<FinanceDTO>> {
        return flow<Resource<FinanceDTO>>{

            val financeDetailsFirestore = firestore
                .collection(AppUser.COLLECTION_NAME)
                .document(id)
                .collection(Constants.SECURE_COLLECTION_NAME)
                .document(Finance.COLLECTION_NAME)
                .get()
                .await()

            val financeDetails = financeDetailsFirestore.toObject(Finance::class.java)



            emit(Resource.Success(financeDetails?.toFinanceDTO()!!))

        }.catch {
            emit(Resource.Error(it.message ?: "error"))
        }.onStart {
            emit(Resource.Loading())
        }
    }
    override suspend fun transferFunds(id: String, value : Double): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>>{

                 firestore
                .collection(AppUser.COLLECTION_NAME)
                .document(DataUtils.user?.value?.id!!)
                .collection(Constants.SECURE_COLLECTION_NAME)
                .document(Finance.COLLECTION_NAME)
                .update("balance", FieldValue.increment(-value))
                .await()


                firestore
                .collection(AppUser.COLLECTION_NAME)
                .document(id)
                .collection(Constants.SECURE_COLLECTION_NAME)
                .document(Finance.COLLECTION_NAME)
                .update("balance", FieldValue.increment(value))
                .await()
            emit(Resource.Success(true))



        }.onStart {
            emit(Resource.Loading())
        }.catch {
            emit(Resource.Error(it.message ?: "error"))
        }
    }
}