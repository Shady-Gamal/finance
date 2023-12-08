package com.example.data.repositoryContracts

import android.util.Log
import com.example.data.model.AppUser
import com.example.domain.entities.DataUtils
import com.example.domain.models.Resource
import com.example.domain.repositories.StorageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URI
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    val storage : FirebaseStorage,
    val firestore: FirebaseFirestore ) : StorageRepository {
    override suspend fun uploadPhoto(fileInputStream: FileInputStream): Flow<Resource<Boolean>> {

        return flow<Resource<Boolean>> {

            val profilePictureUrl = storage.reference.child("users/${DataUtils.user?.id}/profile_picture.jpg")
                .putStream(fileInputStream)
                .await()
                .storage
                .downloadUrl
                .await()
                .toString()

            firestore
                .collection(AppUser.COLLECTION_NAME)
                .document(DataUtils.user?.id!!)
                .update("profilePictureUrl", profilePictureUrl)
                .await()

            DataUtils.user?.profilePictureUrl = profilePictureUrl

            emit(Resource.Success(true))

        }.flowOn(Dispatchers.IO).onStart {
                emit(Resource.Loading())

            }.catch {

                emit(Resource.Error(it.message ?: "error with uploading"))

            Log.e("error", it.message.toString())
        }
    }
}