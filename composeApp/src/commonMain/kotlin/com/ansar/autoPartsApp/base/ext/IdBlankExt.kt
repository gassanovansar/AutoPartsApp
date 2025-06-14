package com.ansar.autoPartsApp.base.ext

fun Int.ifBlank(compare: Int = 0, defaultValue: () -> Int?): Int? {
    return if (this != compare) this else defaultValue()
}
