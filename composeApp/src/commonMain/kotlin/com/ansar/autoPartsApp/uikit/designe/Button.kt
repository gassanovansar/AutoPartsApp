package com.ansar.autoPartsApp.uikit.designe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ansar.autoPartsApp.base.ext.CustomText
import com.ansar.autoPartsApp.uikit.theme.AppTheme


@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textColor: Color = AppTheme.colors.white,
    backgroundColor: Color = AppTheme.colors.mainColor,
    leftContent: @Composable () -> Unit = {},
    rightContent: @Composable () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    textStyle: TextStyle = AppTheme.typography.semiBold.copy(
        fontSize = 16.sp,
        color = if (enabled) textColor else AppTheme.colors.mainColor,
        textAlign = TextAlign.Center,
    ),
    border: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = AppTheme.colors.mainColor,
//            disabledBackgroundColor = AppTheme.colors.disablePrimaryButton,
//            disabledContentColor = AppTheme.colors.disableTextPrimaryButton
        ),
        contentPadding = contentPadding,
        shape = shape,
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        border = border

    ) {
        leftContent()
        CustomText(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = text,
            maxLines = 1,
            style = textStyle
        )
        rightContent()
    }
}