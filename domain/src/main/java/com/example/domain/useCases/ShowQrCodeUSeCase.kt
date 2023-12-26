package com.example.domain.useCases

import android.graphics.Bitmap
import com.example.domain.models.Resource
import com.example.domain.repositories.QrCodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowQrCodeUSeCase @Inject constructor(val qrCodeRepository: QrCodeRepository) {

    operator fun invoke(recipientId : String) :  Flow<Resource<Bitmap>>{

        return qrCodeRepository.showQrCode(recipientId)
    }


}