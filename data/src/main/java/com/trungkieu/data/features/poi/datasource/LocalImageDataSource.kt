package com.trungkieu.data.features.poi.datasource

import android.content.Context
import android.net.Uri
import com.trungkieu.data.core.CacheFolder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.Clock
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

open class LocalImageDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    @CacheFolder protected val cacheDir: File
) : ImageDataSource {

    override suspend fun copyLocalImage(uri: String): String = suspendCoroutine { continuation ->
        val fileName = String.format(LOCAL_IMAGE_NAME, Clock.System.now().toEpochMilliseconds())
        val imageFile = File(cacheDir, fileName)
        imageFile.createNewFile()
        FileOutputStream(imageFile).use { outputStream ->
            getImage(uri).use { inputStream ->
                inputStream?.let { steam ->
                    kotlin.runCatching {
                        copy(steam, outputStream)
                    }.onFailure {
                        continuation.resumeWithException(it)
                    }
                    outputStream.flush()
                    continuation.resume(Uri.fromFile(imageFile).toString())
                } ?: continuation.resumeWithException(NullPointerException("InputStream is null"))
            }
        }
    }

    override suspend fun deleteImage(uri: String) = suspendCoroutine {
        val imageName = Uri.parse(uri).lastPathSegment
        if (imageName != null) {
            val imageFile = File(cacheDir, imageName)
            if (imageFile.exists())
                imageFile.delete()
        }
        it.resume(Unit)
    }

    override fun getImage(uri: String): InputStream? =
        context.contentResolver.openInputStream(Uri.parse(uri))

    @Throws(IOException::class)
    private fun copy(inputStream: InputStream, outputStream: OutputStream) {
        try {
            var length: Int
            val buffer = ByteArray(1024 * 4)
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
        } finally {
            inputStream.close()
            outputStream.close()
        }
    }


    companion object {
        private const val LOCAL_IMAGE_NAME = "poi_image_%s.jpg"
    }
}