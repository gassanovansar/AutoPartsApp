package com.ansar.autoPartsApp.platform

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.content.PartData


interface MultipartManager {
    fun createMultipart(file: Form): MultiPartFormDataContent {
        return createMultipart(listOf(file))
    }

    fun createMultipart(files: List<Form>): MultiPartFormDataContent
    fun createFormData(files: List<Form>): List<PartData>

    fun base64(uri: String): String

    fun base64(uri: List<String>): List<String> {
        return uri.map { base64(it) }
    }

}

sealed interface Form {
    class FormFile(
        val key: String,
        val uri: String
    ) : Form

    class FormBody(
        val key: String,
        val value: Any,
    ) : Form
}

sealed interface FormFull {
    class FormFileFull(
        val key: String,
        val name: String,
        val byteArray: ByteArray
    ) : FormFull

    class FormBodyFull(
        val key: String,
        val value: Any,
    ) : FormFull
}


