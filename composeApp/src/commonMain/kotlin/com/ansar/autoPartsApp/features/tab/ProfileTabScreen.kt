package com.ansar.autoPartsApp.features.tab

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.ansar.autoPartsApp.features.history.HistoryScreen
import com.ansar.autoPartsApp.features.main.MainScreen
import com.ansar.autoPartsApp.features.profile.ProfileScreen
import com.ansar.autoparts.images.AppResourceImages
import io.github.skeptick.libres.compose.painterResource

internal object ProfileTabScreen : Tab {

    override val key: ScreenKey = "MainTabScreen"

    @Composable
    override fun Content() {
        Navigator(ProfileScreen())
    }


    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1.toUShort(),
            title = "Профиль",
            icon = AppResourceImages.profile.painterResource()
        )
}