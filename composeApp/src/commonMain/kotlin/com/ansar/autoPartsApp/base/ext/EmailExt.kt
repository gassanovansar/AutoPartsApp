package com.ansar.autoPartsApp.base.ext

private val OnlyEnglish = "^[a-zA-Z]+\$"

private val EmailRegex =
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"


private fun String.onlyEnglish(): Boolean {
    return OnlyEnglish.toRegex().matches(this.filter { it.isLetter() })
}

fun String.isEmail(): Boolean {
    return EmailRegex.toRegex().matches(this) && this.onlyEnglish()
}

private val PasswordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"

fun String.isValidPass(): Boolean {
    return this.length >= 8 && this.onlyEnglish()
}