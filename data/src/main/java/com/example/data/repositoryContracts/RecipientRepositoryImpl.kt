package com.example.data.repositoryContracts

import android.util.Log
import com.example.data.firestore.fetchRecipients
import com.example.data.firestore.saveRecipient
import com.example.data.model.toARecipientDTO
import com.example.domain.entities.RecipientDTO
import com.example.domain.models.Resource
import com.example.domain.repositories.RecipientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RecipientRepositoryImpl @Inject constructor() : RecipientRepository {
    override suspend fun addRecipient(recipientId: String): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>> {
            saveRecipient(recipientId)
            emit(Resource.Success(true))
        }.onStart {
            emit(Resource.Loading())
        }.catch {
            emit(Resource.Error(it.message ?: "an error has occured"))
        }
    }

    override suspend fun getRecipients(): Flow<Resource<MutableList<RecipientDTO>?>> {
        return flow<Resource<MutableList<RecipientDTO>?>> {

            val result = fetchRecipients()
            Log.e("TAG2", result?.get(0)?.recipientFullName.toString())
            emit(Resource.Success(result?.map {
                it.toARecipientDTO()
            }?.toMutableList()))
        }.catch {

            emit(Resource.Error(it.message ?: "error has occured"))
        }
            .onStart {
                emit(Resource.Loading())

            }
    }


}