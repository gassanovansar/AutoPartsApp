package com.ansar.autoPartsApp.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ansar.autoPartsApp.base.ext.CustomText
import com.ansar.autoPartsApp.base.navigation.RootNavigator
import com.ansar.autoPartsApp.features.auth.AuthScreen
import com.ansar.autoPartsApp.uikit.designe.PrimaryButton
import com.ansar.autoPartsApp.uikit.designe.toolBar.Toolbar
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoPartsApp.uikit.theme.AppTheme

class ProfileScreen : Screen {
    @Composable
    override fun Content() {

        val viewModel = rememberScreenModel { ProfileViewModel() }
        val state = viewModel.stateFlow.collectAsState()
        val rootNavigator = RootNavigator.currentOrThrow

        LaunchedEffect(viewModel) {
            viewModel.getProfile()
        }
        PageContainer(
            header = {
                Toolbar(
                    title = "Профиль",
                    line = true
                )
            }, content = {

                Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {

                    Row(Modifier.padding(top = 12.dp)) {
                        CustomText(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "ФИО :",
                            style = AppTheme.typography.bold.copy(
                                fontSize = 18.sp,
                                color = AppTheme.colors.text,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        CustomText(
                            modifier = Modifier.padding(horizontal = 4.dp)
                                .align(Alignment.CenterVertically),
                            text = viewModel.sessionManager.profile?.manager?.fullName.orEmpty(),
                            style = AppTheme.typography.medium.copy(
                                fontSize = 18.sp,
                                color = AppTheme.colors.text,
                            ),
                        )
                    }
                    Row(Modifier.padding(top = 12.dp)) {
                        CustomText(
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            text = "Контакты :",
                            style = AppTheme.typography.bold.copy(
                                fontSize = 18.sp,
                                color = AppTheme.colors.text,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        CustomText(
                            modifier = Modifier.padding(horizontal = 4.dp)
                                .align(Alignment.CenterVertically),
                            text = viewModel.sessionManager.profile?.manager?.phone.orEmpty(),
                            style = AppTheme.typography.medium.copy(
                                fontSize = 18.sp,
                                color = AppTheme.colors.text,
                            ),
                        )
                    }
                }
            }, footer = {
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