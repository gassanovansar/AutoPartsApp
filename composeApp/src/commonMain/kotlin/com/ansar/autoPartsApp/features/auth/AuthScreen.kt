package com.ansar.autoPartsApp.features.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ansar.autoPartsApp.features.main.MainScreen
import com.ansar.autoPartsApp.features.tab.TabScreen
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import com.ansar.autoPartsApp.uikit.designe.BaseTextFiled
import com.ansar.autoPartsApp.uikit.designe.AppTextFiledType
import com.ansar.autoPartsApp.uikit.designe.PrimaryButton
import kotlinx.coroutines.launch


class AuthScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { AuthViewModel() }
        val state by viewModel.stateFlow.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(viewModel) {
            launch {
                viewModel.container.sideEffectFlow.collect {
                    navigator.replaceAll(TabScreen())
                }
            }
        }
        PageContainer(
            isLoading = viewModel.status.collectAsState(false),
            error = viewModel.error.collectAsState(initial = null),
            content = {
                Column(Modifier.align(alignment = Alignment.Center)) {
                    Box(
                        Modifier.size(32.dp).align(Alignment.CenterHorizontally)
                            .background(AppTheme.colors.mainColor)
                    )
                    Text(
                        text = "Войдите в свой аккаунт",
                        style = AppTheme.typography.bold.copy(
                            fontSize = 16.sp,
                            color = AppTheme.colors.text
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )

                    Card(
                        modifier = Modifier.padding(horizontal = 48.dp).padding(top = 32.dp),
                        border = BorderStroke(1.dp, AppTheme.colors.border),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column {
                            BaseTextFiled(
                                value = state.login,
                                hint = "Login",
                                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                                error = state.hasLoginError
                            ) { viewModel.changeLogin(it) }
                            BaseTextFiled(
                                value = state.password,
                                hint = "Password",
                                shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp),
                                type = AppTextFiledType.PASSWORD,
                                error = state.hasPasswordError
                            ) { viewModel.changePassword(it) }
                        }
                    }
                    PrimaryButton(
                        text = "Войти",
                        modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
                            .padding(horizontal = 48.dp)
                    ) {
                        viewModel.auth()
                    }
                }
            })

    }

}

