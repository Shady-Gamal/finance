package com.example.data.firestore

import com.example.data.model.AppUser
import com.example.domain.entities.AppUserDTO
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

fun getCollectionRef(collectionName: String): CollectionReference {
    return FirebaseFirestore.getInstance()
        .collection(collectionName)}


suspend fun addUser(
    uid: String?,
    user: AppUserDTO
) {
   val docRef = getCollectionRef(AppUser.COLLECTION_NAME)
        .document(uid!!)
        user.id = docRef.id
        docRef.set(user)
        .await()

}

suspend fun getUser(
    uid: String?
) : AppUser? {
     val user = getCollectionRef(AppUser.COLLECTION_NAME)
        .document(uid!!)
        .get()
        .await()

    return user.toObject(AppUser::class.java)
}