package com.ansar.autoPartsApp.uikit.designe.toolBar

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoparts.AppResource
import io.github.skeptick.libres.compose.painterResource


@Composable
fun BackIcon(modifier: Modifier = Modifier,color: Color? = null,onClick: (() -> Unit)? = null) {
    val navigator = LocalNavigator.currentOrThrow
    Image(
        painter = AppResource.image.back.painterResource(),
        contentDescription = null,
        modifier = modifier.clickableRound(32.dp) {
            if (onClick == null) navigator.pop()
            else onClick()
        },
        colorFilter =if (color==null) null else  ColorFilter.tint(color = color)
    )
}

