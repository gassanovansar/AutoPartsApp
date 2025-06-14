package com.ansar.autoPartsApp.platform

import com.ansar.autoPartsApp.base.ext.formData
import com.ansar.autoPartsApp.base.ext.multipart
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.content.PartData
import io.ktor.util.encodeBase64
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSData
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import platform.Foundation.*
import platform.posix.memcpy

class MultipartManagerImpl : MultipartManager {


    override fun createMultipart(files: List<Form>): MultiPartFormDataContent {
        return multipart(files.map {
            when (it) {
                is Form.FormBody -> {
                    FormFull.FormBodyFull(it.key, it.value)
                }

                is Form.FormFile -> {
                    val bytes = NSData.dataWithContentsOfFile(it.uri)?.toByteArray()
                    FormFull.FormFileFull(
                        key = it.key,
                        name = it.uri,
                        byteArray = bytes ?: byteArrayOf()
                    )
                }
            }
        })
    }

    override fun createFormData(files: List<Form>): List<PartData> {
        return formData(files.map {
            when (it) {
                is Form.FormBody -> {
                    FormFull.FormBodyFull(it.key, it.value)
                }

                is Form.FormFile -> {
                    val bytes = NSData.dataWithContentsOfFile(it.uri)?.toByteArray()
                    FormFull.FormFileFull(
                        key = it.key,
                        name = it.uri,
                        byteArray = bytes ?: byteArrayOf()
                    )
                }
            }
        })
    }

    override fun base64(uri: String): String {
        val bytes = NSData.dataWithContentsOfFile(uri)?.toByteArray() ?: byteArrayOf()
        return bytes.encodeBase64()
    }


}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray = ByteArray(this@toByteArray.length.toInt()).apply {
    usePinned {
        memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
    }
}

@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toNSData(): NSData = memScoped {
    NSData.create(
        bytes = allocArrayOf(this@toNSData),
        length = this@toNSData.size.toULong()
    )
}

fun getCacheDir(): String {
    return (NSFileManager.defaultManager().URLsForDirectory(9.toULong(), 1.toULong())
        .firstOrNull() as? NSURL)?.path + "/"
}
