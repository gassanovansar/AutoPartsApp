package com.ansar.autoPartsApp.data.mapper

import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.data.model.BrandResponse
import com.ansar.autoPartsApp.domain.model.BrandUI

fun BrandResponse.toUI(): SelectableDropDownItem {
    return SelectableDropDownItem(
        BrandUI(
            id = this.id ?: 0,
            title = this.title.orEmpty()
        ), false
    )
}