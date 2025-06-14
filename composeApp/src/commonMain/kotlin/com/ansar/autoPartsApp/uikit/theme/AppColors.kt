package com.ansar.autoPartsApp.uikit.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

interface AppColors {
    val mainColor: Color
    val text: Color
    val background: Color
    val border: Color
    val borderClick: Color
    val hint: Color
    val white: Color
}


class AppColorsImpl(
    override val mainColor: Color, override val text: Color, override val background: Color,
    override val border: Color,
    override val borderClick: Color,
    override val hint: Color, override val white: Color


) : AppColors

internal fun appLightColors() = AppColorsImpl(
    mainColor = Color(0xFF263052),
    text = Color(0xFF212529),
    background = Color(0xFFF8F9FA),
    border = Color(0xFFCED4DA),
    borderClick = Color(0xFF99A3B7),
    hint = Color(0xFF6E767E),
    white = Color(0xFFFFFFFF)

)


internal fun appDarkColors() = AppColorsImpl(
    mainColor = Color(0xFF263052),
    text = Color(0xFF212529),
    background = Color(0xFFF8F9FA),
    border = Color(0xFFCED4DA),
    borderClick = Color(0xFF99A3B7),
    hint = Color(0xFF6E767E),
    white = Color(0xFFFFFFFF)


)


internal val LocalColors = compositionLocalOf<AppColors> {
    error(
        "No colors provided! Make sure to wrap all usages of components in a " +
                "AppTheme."
    )
}
