package com.ansar.autoPartsApp.base

import com.ansar.autoPartsApp.uikit.designe.DropDown

data class SelectableItem<T>(
    val data: T,
    val isSelected: Boolean
)

data class SelectableDropDownItem(
    val data: DropDown,
    val isSelected: Boolean
)

fun SelectableItem<String>.ifBlank(
    action: () -> SelectableItem<String>
): SelectableItem<String> {
    return if (this.data.isBlank()) action() else this
}