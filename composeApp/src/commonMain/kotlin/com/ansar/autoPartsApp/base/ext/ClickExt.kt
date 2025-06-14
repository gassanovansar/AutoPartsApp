package com.ansar.autoPartsApp.base.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp


fun Modifier.clickableRound(
    radius: Dp,
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier {
    return clip(RoundedCornerShape(radius))
        .clickable(enabled = enabled) {
            onClick()
        }
}

fun Modifier.clickableRound(
    clip: RoundedCornerShape,
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier {
    return clip(clip)
        .clickable(enabled = enabled) {
            onClick()
        }
}

//@Composable
//fun Modifier.clickableApp(
//    shimmer: Boolean = false,
//    onClick: () -> Unit
//): Modifier {
//    return when (Platform.type) {
//        PlatformType.ANDROID -> {
//            clickable(
//                enabled = !shimmer
//            ) {
//                onClick()
//            }
//        }
//
//        PlatformType.IOS -> {
//            clickable(
//                enabled = !shimmer,
//                indication = null,
//                interactionSource = remember { MutableInteractionSource() }) {
//                onClick()
//            }
//        }
//    }
//}