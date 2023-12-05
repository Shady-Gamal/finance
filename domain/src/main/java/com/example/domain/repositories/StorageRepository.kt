package com.example.domain.repositories

import com.example.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URI

interface StorageRepository {


    suspend fun uploadPhoto(fileInputStream: FileInputStream ) : Flow<Resource<Boolean>>
}