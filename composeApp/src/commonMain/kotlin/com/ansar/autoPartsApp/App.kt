package com.ansar.autoPartsApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.ansar.autoPartsApp.domain.manager.Notification
import com.ansar.autoPartsApp.domain.manager.NotificationManager
import com.ansar.autoPartsApp.features.main.MainScreen
import com.ansar.autoPartsApp.uikit.components.NotificationCenter
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import org.koin.mp.KoinPlatform

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun App() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            BottomSheetNavigator(
                sheetShape = RoundedCornerShape(
                    topStartPercent = 8,
                    topEndPercent = 8
                )
            ) {
                Navigator(MainScreen())
//                DebugView()
            }
            NotificationContainer()
        }

    }
}

@Composable
private fun NotificationContainer() {
    val notificationManager by KoinPlatform.getKoin().inject<NotificationManager>()
    var notification by remember { mutableStateOf<Notification?>(null) }
    LaunchedEffect(notificationManager) {
        notificationManager.notification.consumeAsFlow().collectLatest {
            notification = it
        }
    }
    NotificationCenter(notification)
}