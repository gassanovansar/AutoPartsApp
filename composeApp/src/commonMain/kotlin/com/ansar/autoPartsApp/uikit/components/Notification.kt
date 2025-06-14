package com.ansar.autoPartsApp.uikit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ansar.autoPartsApp.domain.manager.Notification
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import kotlinx.coroutines.delay


@Composable
fun NotificationCenter(notification: Notification?) {

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(notification) {
        visible = notification != null
        delay(4000L)
        visible = false

    }


    val density = LocalDensity.current
    AnimatedVisibility(
        modifier = Modifier.padding(top = 16.dp).padding(16.dp).fillMaxWidth(),
        visible = visible,
        enter = slideInVertically {
            // Slide in from 40 dp from the top.
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()

    ) {
        Card(shape = RoundedCornerShape(16.dp), elevation = 4.dp) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Image(
//                    painter = when (notification) {
//                        is Notification.Failure -> AppResourceImages.notificationfailure.painterResource()
//                        is Notification.Success -> AppResourceImages.notificationsuccess.painterResource()
//                        null -> TODO()
//                    },
//                    contentDescription = null
//                )
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = notification?.header ?: "",
                        style = AppTheme.typography.bold.copy(
                            fontSize = 18.sp,
                            lineHeight = 25.sp,
//                            color = AppTheme.colors.greyBlack,
                        )
                    )
                    Text(
                        text = notification?.message ?: "",
                        style = AppTheme.typography.regular.copy(
                            fontSize = 14.sp,
                            lineHeight = 19.sp,
//                            color = AppTheme.colors.greyBlack,
                        )
                    )
                }
            }
        }
    }

}
