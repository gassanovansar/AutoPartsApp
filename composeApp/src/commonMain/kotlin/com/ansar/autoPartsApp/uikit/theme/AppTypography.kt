package com.ansar.autoPartsApp.uikit.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.PlatformSpanStyle
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import com.ansar.autoPartsApp.platform.Platform
import com.ansar.autoPartsApp.platform.PlatformType
import com.ansar.autoPartsApp.uikit.theme.typography.BaseTypography

expect object AppFonts {
    val regular: BaseTypography
    val medium: BaseTypography
    val semiBold: BaseTypography
    val bold: BaseTypography
//    val black: BaseTypography
}


class AppTypography(
    val regular: TextStyle,
    val medium: TextStyle,
    val semiBold: TextStyle,
    val bold: TextStyle,
//    val black: TextStyle,
)


@Composable
fun textStyles(): AppTypography {
    return AppTypography(
        regular = AppFonts.regular.getComposeTextStyle(),
        medium = AppFonts.medium.getComposeTextStyle(),
        semiBold = AppFonts.semiBold.getComposeTextStyle(),
        bold = AppFonts.bold.getComposeTextStyle(),
//        black = AppFonts.black.getComposeTextStyle(),
    )
}

private fun toTextStyle(typographyStyle: BaseTypography): TextStyle {
    return TextStyle(
        fontSize = typographyStyle.fontSize,
        fontFamily = typographyStyle.fontFamily,
        lineHeight = typographyStyle.lineHeight,
        fontWeight = typographyStyle.fontWeight,
        platformStyle = PlatformTextStyle(
            PlatformSpanStyle.Default,
            PlatformParagraphStyle.Default
        ),
        baselineShift = BaselineShift(typographyStyle.baselineShift),
        lineHeightStyle = if (Platform.type == PlatformType.ANDROID) {
            LineHeightStyle(
                LineHeightStyle.Alignment.Center,
                LineHeightStyle.Trim.None
            )
        } else null
    )
}

fun BaseTypography.getComposeTextStyle(): TextStyle {
    return toTextStyle(this)
}

internal val LocalTypography = compositionLocalOf<AppTypography> {
    error(
        "No typography provided! Make sure to wrap all usages of components in a " +
                "AppTheme."
    )
}
