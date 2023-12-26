package com.example.domain.useCases

import com.example.domain.models.Resource
import com.example.domain.repositories.QrCodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ScanQrCodeUseCase @Inject constructor( val qrCodeRepository: QrCodeRepository) {
    suspend operator fun invoke() : Flow<Resource<String>>{

        return qrCodeRepository.scanQrCode()

    }
}