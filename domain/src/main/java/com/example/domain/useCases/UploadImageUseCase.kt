package com.example.domain.useCases

import com.example.domain.models.Resource
import com.example.domain.repositories.StorageRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class UploadImageUseCase(private val storageRepository: StorageRepository)  {

    suspend operator fun invoke(fileInputStream: FileInputStream ) : Flow<Resource<Boolean>>{

        return storageRepository.uploadPhoto(fileInputStream)

    }
}