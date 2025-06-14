package com.ansar.autoPartsApp.uikit.designe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ansar.autoPartsApp.uikit.theme.AppTheme

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10.dp),
    backgroundColor: Color = AppTheme.colors.white,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        modifier = modifier,
        backgroundColor = backgroundColor,
        shape = shape,
        border = border,
        elevation = elevation
    ) {
        Box(modifier = Modifier) {
            content()
        }

    }
}