package com.ansar.autoPartsApp.uikit.designe.toolBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ansar.autoPartsApp.base.ext.start
import com.ansar.autoPartsApp.uikit.theme.AppTheme


@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    startTitle: String? = null,
    line: Boolean = false,
    centerContent: @Composable () -> Unit = {},
    leftIcon: @Composable () -> Unit = {},
    rightIcon: @Composable () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxWidth().background(AppTheme.colors.background)
            .height(40.dp)
            .padding(horizontal = 16.dp),
    ) {
        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            leftIcon()
        }
        if (startTitle != null) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart).start(29.dp),
                text = startTitle,
                style = AppTheme.typography.regular.copy(
                    fontSize = 14.sp,
                    color = AppTheme.colors.text,
                )
            )
        } else {
            Box(modifier = Modifier.align(Alignment.Center)) {
                if (title != null) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = AppTheme.colors.text,
                        style = AppTheme.typography.regular.copy(
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                } else {
                    Box(modifier = Modifier.padding(start = 8.dp)) {
                        centerContent()
                    }
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            rightIcon()
        }


    }

    if (line) {
        Box(
            modifier = Modifier.fillMaxWidth().height(1.dp)
                .background(AppTheme.colors.mainColor)
        )
    }

}

