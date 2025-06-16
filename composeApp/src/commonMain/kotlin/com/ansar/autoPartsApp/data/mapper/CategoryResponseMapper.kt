package com.ansar.autoPartsApp.data.mapper

import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.data.model.BrandResponse
import com.ansar.autoPartsApp.data.model.CategoryResponse
import com.ansar.autoPartsApp.domain.model.BrandUI
import com.ansar.autoPartsApp.domain.model.CategoryUI

fun CategoryResponse.toUI(): SelectableDropDownItem {
    return SelectableDropDownItem(
        CategoryUI(
            id = this.id ?: 0,
            title = this.title.orEmpty()
        ), false
    )
}