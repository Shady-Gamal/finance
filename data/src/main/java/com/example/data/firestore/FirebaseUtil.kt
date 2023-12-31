package com.example.data.firestore

import android.util.Log
import com.example.data.model.AppUser
import com.example.data.model.Recipient
import com.example.domain.entities.AppUserDTO
import com.example.domain.entities.DataUtils.user
import com.example.domain.entities.FinanceDTO
import com.example.domain.entities.RecipientDTO
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

fun getCollectionRef(collectionName: String): CollectionReference {
    return FirebaseFirestore.getInstance()
        .collection(collectionName)

}


suspend fun addUser(
    uid: String?,
    user: AppUserDTO
) {
   val docRef = getCollectionRef(AppUser.COLLECTION_NAME)
        .document(uid!!)

        user.id = docRef.id
        docRef.set(user)
        .await()


    docRef.collection(Constants.SECURE_COLLECTION_NAME)
        .document(Constants.FINANCE_COLLECTION_NAME)
        .set(FinanceDTO())
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

suspend fun saveRecipient(recipientId : String) {

    val recipientInfo = getUser(recipientId)


    if (recipientInfo != null) {

        getCollectionRef(Recipient.COLLECTION_NAME)
            .document(user?.value?.id!!)
            .collection("MyRecipients")
            .document(recipientId)
            .set(
                Recipient(
                    user?.value?.id!!,
                    recipientInfo.id,
                    recipientInfo.fullName,
                    recipientInfo.profilePictureUrl
                )
            )
            .await()
    }
}

suspend fun fetchRecipients() : MutableList<Recipient> {

    val recipientList  = getCollectionRef(Recipient.COLLECTION_NAME)
        .document(user?.value?.id!!)
        .collection("MyRecipients")
        .get()
        .await()

    return recipientList.toObjects(Recipient::class.java)

}