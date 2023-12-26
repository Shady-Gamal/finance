package com.example.data.repositoryContracts

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import com.example.domain.models.Resource
import com.example.domain.repositories.QrCodeRepository
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QrCodeRepositoryImpl @Inject constructor(
    @ApplicationContext val context : Context
) : QrCodeRepository {
    override suspend fun scanQrCode(): Flow<Resource<String>> {
        return flow<Resource<String>>{

            val scanner = GmsBarcodeScanning.getClient(context)

           val result = scanner.startScan().await()

            emit(Resource.Success(result.rawValue!!))

        }.catch {
            emit(Resource.Error(it.message ?: "error"))
        }
    }

    override fun showQrCode(recipientId: String): Flow<Resource<Bitmap>> {
        return flow<Resource<Bitmap>> {

            val size = 512 //pixels
            val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
            val bits = QRCodeWriter().encode(recipientId, BarcodeFormat.QR_CODE, size, size, hints)
            val bitmap =  Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
                for (x in 0 until size) {
                    for (y in 0 until size) {
                        it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
            }

            emit(Resource.Success(bitmap))

        }.catch {

            emit(Resource.Error(it.message ?: "error"))
        }.onStart {

            emit(Resource.Loading())
        }
    }
}