package com.ansar.autoPartsApp.features.product

sealed interface ProductEvent {
    data object Back : ProductEvent
}