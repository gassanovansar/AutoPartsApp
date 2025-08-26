package com.ansar.autoPartsApp.features.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ansar.autoPartsApp.base.navigation.RootNavigator
import com.ansar.autoPartsApp.features.auth.AuthScreen
import com.ansar.autoPartsApp.uikit.designe.PrimaryButton
import com.ansar.autoPartsApp.uikit.designe.toolBar.Toolbar
import com.ansar.autoPartsApp.uikit.screens.PageContainer

class ProfileScreen : Screen {
    @Composable
    override fun Content() {

        val viewModel = rememberScreenModel { ProfileViewModel() }
        val rootNavigator = RootNavigator.currentOrThrow
        PageContainer(
            header = {
                Toolbar(
                    title = "Профиль",
                    line = true
                )
            }, content = {}, footer = {
                PrimaryButton(
                    text = "Выход из аккаунта",
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    viewModel.sessionManager.exit()
                    rootNavigator.replaceAll(AuthScreen())
                }
            }
        )

    }
}