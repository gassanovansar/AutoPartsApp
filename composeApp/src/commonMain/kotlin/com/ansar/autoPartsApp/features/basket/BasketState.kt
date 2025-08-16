package com.ansar.autoPartsApp.features.basket

import com.ansar.autoPartsApp.domain.model.CartUI

data class BasketState(
    val cart: List<CartUI>,
    val price: Int,
    val emptyText: Boolean,
) {
    companion object {
        val Default = BasketState(cart = emptyList(), price = 0, emptyText = false)
    }
}