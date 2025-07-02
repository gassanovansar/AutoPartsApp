package com.ansar.autoPartsApp.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ansar.autoPartsApp.features.auth.AuthScreen
import com.ansar.autoPartsApp.features.tab.TabScreen
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoparts.images.AppResourceImages
import io.github.skeptick.libres.compose.painterResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { SplashScreenModel() }
        LaunchedEffect(viewModel) {
            launch {
                viewModel.container.sideEffectFlow.collect {
                    delay(1000)
                    handleSideEffect(navigator, it)
                }
            }
        }
        PageContainer(
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = AppResourceImages.newicon.painterResource(),
                        contentDescription = null,
                        modifier = Modifier.padding(top = 100.dp).align(Alignment.TopCenter)
                            .size(250.dp)
                    )
                }
            })

    }


    private fun handleSideEffect(navigator: Navigator, sideEffect: SplashEvent) {
        when (sideEffect) {
            SplashEvent.Main -> {
                navigator.replaceAll(TabScreen())
            }

            SplashEvent.Auth -> {
                navigator.replaceAll(AuthScreen())
            }
        }
    }
}
