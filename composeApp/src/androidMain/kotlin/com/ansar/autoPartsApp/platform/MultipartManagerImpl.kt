package com.ansar.autoPartsApp.platform

import android.content.Context
import android.net.Uri
import android.util.Base64
import com.ansar.autoPartsApp.util.RealPathUtil
import com.ansar.autoPartsApp.base.ext.formData
import com.ansar.autoPartsApp.base.ext.multipart
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.content.PartData
import java.io.File


class MultipartManagerImpl(private val context: Context) : MultipartManager {

    override fun createMultipart(files: List<Form>): MultiPartFormDataContent {
        return multipart(files.map {
            when (it) {
                is Form.FormBody -> {
                    FormFull.FormBodyFull(it.key, it.value)
                }

                is Form.FormFile -> {
                    val file = file(context, it.uri)
                    FormFull.FormFileFull(
                        key = it.key,
                        name = file?.name.orEmpty(),
                        byteArray = file?.readBytes() ?: byteArrayOf()
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
                    val file = file(context, it.uri)
                    FormFull.FormFileFull(
                        key = it.key,
                        name = file?.name.orEmpty(),
                        byteArray = file?.readBytes() ?: byteArrayOf()
                    )
                }
            }
        })
    }

    override fun base64(uri: String): String {
        return Base64.encodeToString(file(context, uri)?.readBytes(), Base64.NO_WRAP)
    }

    private fun file(context: Context, uri: String?): File? {
        return try {
            val path = RealPathUtil.getRealPath(context, Uri.parse(uri))
            File(path)
        } catch (e: Exception) {
            null
        }
    }

}