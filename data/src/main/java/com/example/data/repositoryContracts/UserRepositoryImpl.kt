package com.example.data.repositoryContracts

import com.example.data.firestore.addUser
import com.example.data.firestore.getUser
import com.example.data.model.AppUser
import com.example.data.model.toAppUserDTO
import com.example.domain.entities.AppUserDTO
import com.example.domain.entities.DataUtils
import com.example.domain.models.Resource
import com.example.domain.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    val firestore: FirebaseFirestore
) : UserRepository {
    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>> {
      auth.signInWithEmailAndPassword(email, password).await()

            emit(Resource.Success(true))
        }.onStart {
                    emit(Resource.Loading())
        }.catch { emit(Resource.Error(it.message ?: "An error has occurd")) }
            }

    override suspend fun singUpWithEmailAndPassword(
        email: String,
        password: String,
        user : AppUserDTO
    ): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>>  {
            val result = auth.createUserWithEmailAndPassword(email,password).await()
            val uid = result.user?.uid
            addUser(uid, user)

            emit(Resource.Success(true))
        }.onStart { emit(Resource.Loading()) }
            .catch { emit (Resource.Error(it.message ?: "error")) }
    }

    override suspend fun signOut(): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>> {

            auth.signOut()
            emit(Resource.Success(true))

        }.onStart {
            emit(Resource.Loading())
        }.catch {
            emit(Resource.Error(it.message ?: "an error has ocured"))
        }
    }

    override suspend fun updateUserData(user: AppUserDTO): Flow<Resource<Boolean>> {

        return flow<Resource<Boolean>>{

            firestore
                .collection(AppUser.COLLECTION_NAME)
                .document(DataUtils.user?.value?.id!!)
                .set(user)
                .await()
            emit(Resource.Success(true))

        }.onStart {
            emit(Resource.Loading())
        }.catch {
            emit(Resource.Error(it.message ?: "error"))
        }

    }

    override suspend fun getUserData(id: String): Flow<Resource<AppUserDTO>> {
        return flow<Resource<AppUserDTO>> {

            val user = getUser(id)
            emit(Resource.Success(user?.toAppUserDTO()!!))
        }.onStart {
            emit(Resource.Loading())
        }.catch {
            emit(Resource.Error(it.message ?: "error"))
        }

    }
}
