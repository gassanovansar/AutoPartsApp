package com.ansar.autoPartsApp.base

data class SelectableItem<T>(
    val data: T,
    val isSelected: Boolean
)

fun SelectableItem<String>.ifBlank(
    action: () -> SelectableItem<String>
): SelectableItem<String> {
    return if (this.data.isBlank()) action() else this
}