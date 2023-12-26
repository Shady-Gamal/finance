package com.example.domain.repositories

import android.graphics.Bitmap
import com.example.domain.models.Resource
import kotlinx.coroutines.flow.Flow


interface QrCodeRepository {

    suspend fun scanQrCode () : Flow<Resource<String>>

    fun showQrCode (recipientId : String) : Flow<Resource<Bitmap>>
}