package com.ansar.autoPartsApp.features.history

import com.ansar.autoPartsApp.domain.model.OrderUI

data class HistoryState(
    val orders: List<OrderUI>,
    val emptyText: Boolean,
    val page: Int,
    val limit: Int
) {
    companion object {
        val Default = HistoryState(
            orders = emptyList(),
            page = 1,
            limit = 20,
            emptyText = false
        )
    }
}