package com.ansar.autoPartsApp.features.tab

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.ansar.autoPartsApp.features.basket.BasketScreen
import com.ansar.autoparts.images.AppResourceImages
import io.github.skeptick.libres.compose.painterResource

object BasketTabScreen  : Tab {

    override val key: ScreenKey = "BasketTabScreen"

    @Composable
    override fun Content() {
        Navigator(BasketScreen())
    }


    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1.toUShort(),
            title = "Корзина",
            icon = AppResourceImages.purchases.painterResource()
        )
}