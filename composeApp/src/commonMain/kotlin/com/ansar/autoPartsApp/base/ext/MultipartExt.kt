package com.ansar.autoPartsApp.base.ext

import com.ansar.autoPartsApp.platform.FormFull
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.util.InternalAPI


fun multipart(files: List<FormFull>): MultiPartFormDataContent {
    return MultiPartFormDataContent(createFormData(files))

}

fun formData(files: List<FormFull>): List<PartData> {
    return createFormData(files)
}

@OptIn(InternalAPI::class)
fun createFormData(files: List<FormFull>): List<PartData> {
    return formData {
        files.forEach {
            when (it) {
                is FormFull.FormBodyFull -> {
                    append(it.key, it.value)
                }

                is FormFull.FormFileFull -> {
                    append(
                        key = it.key,
                        value = it.byteArray,
                        headers = Headers.build {
                            append(
                                HttpHeaders.ContentType,
//                                "multipart/form-data"
                                "image/png"
                            ) // Mime type required
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=${it.name}"
                            ) // Filename in content disposition required
                        }
                    )
                }
            }

        }

    }
}