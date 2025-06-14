package com.ansar.autoPartsApp.base.ext

import com.ansar.autoPartsApp.uikit.screens.toDate
import com.ansar.autoPartsApp.uikit.screens.toZero


fun String.date(): String {
    val date = this.take(10).split("-")
    return if (date.size == 3) "${date[2]}.${date[1]}.${date[0]}"
    else this.take(10)
}

fun String.clock(): String {
    return if (this.length >= 14) this.subSequence(11, 16).toString() else ""
}

fun Long?.date(): String {
    val (day, month, year) = this?.toDate() ?: Triple(0, 0, 0)
    return if (this != null) "${day.toZero()}.${month.toZero()}.${year}" else ""
}

fun Long?.dateApi(): String {
    val (day, month, year) = this?.toDate() ?: Triple(0, 0, 0)
    return if (this != null) "${year}-${month.toZero()}-${day.toZero()}" else ""
}