package com.ansar.autoPartsApp.uikit.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Typeface
import com.ansar.autoPartsApp.uikit.theme.typography.BaseTypography
import com.ansar.autoPartsApp.uikit.theme.typography.TextStyleBold
import com.ansar.autoPartsApp.uikit.theme.typography.TextStyleMedium
import com.ansar.autoPartsApp.uikit.theme.typography.TextStyleRegular
import com.ansar.autoPartsApp.uikit.theme.typography.TextStyleSemiBold
import org.jetbrains.skia.FontSlant
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.FontWeight


private fun loadCustomFont(
    name: String,
    font: FontStyle
): org.jetbrains.skia.Typeface {

    return org.jetbrains.skia.Typeface.makeFromName(name, font)
}

private fun Int.style(): FontStyle {
    return FontStyle(
        weight = this,
        width = 0,
        slant = FontSlant.UPRIGHT
    )
}

actual object AppFonts {

    actual val regular: BaseTypography =
        TextStyleRegular(
            FontFamily(
                Typeface(
                    loadCustomFont(
                        name = "SF Pro Display1",
                        font = FontWeight.NORMAL.style()
                    )
                )
            )
        )

    actual val medium: BaseTypography = TextStyleMedium(
        FontFamily(
            Typeface(
                loadCustomFont(
                    name = "SF Pro Display",
                    font = FontWeight.NORMAL.style()
                )
            )
        )
    )
    actual val semiBold: BaseTypography = TextStyleSemiBold(
        FontFamily(
            Typeface(
                loadCustomFont(
                    name = "SF Pro Display",
                    font = FontWeight.SEMI_BOLD.style()
                )
            )
        )
    )
    actual val bold: BaseTypography = TextStyleBold(
        FontFamily(
            Typeface(
                loadCustomFont(
                    name = "SF Pro Display",
                    font = FontWeight.BOLD.style()
                )
            )
        )
    )
//
//    actual val black: BaseTypography = TextStyleBlack(
//        FontFamily(
//            Typeface(
//                loadCustomFont(
//                    name = "SF Pro Display",
//                    font = FontWeight.BLACK.style()
//                )
//            )
//        )
//    )
}