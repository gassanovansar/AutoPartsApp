package com.ansar.autoPartsApp.uikit.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.ansar.autoPartsApp.uikit.theme.typography.BaseTypography
import com.ansar.autoPartsApp.uikit.theme.typography.TextStyleBold
import com.ansar.autoPartsApp.uikit.theme.typography.TextStyleMedium
import com.ansar.autoPartsApp.uikit.theme.typography.TextStyleRegular
import com.ansar.autoPartsApp.uikit.theme.typography.TextStyleSemiBold
import com.ansar.autoparts.R

actual object AppFonts {
    //    actual val small: BaseTypography = TextStyleRegular(FontFamily(Font(R.font.inter_400)))
    actual val regular: BaseTypography = TextStyleRegular(FontFamily(Font(R.font.font_400)))
    actual val medium: BaseTypography = TextStyleMedium(FontFamily(Font(R.font.font_500)))
    actual val semiBold: BaseTypography = TextStyleSemiBold(FontFamily(Font(R.font.font_600)))
    actual val bold: BaseTypography = TextStyleBold(FontFamily(Font(R.font.font_700)))
//    actual val black: BaseTypography = TextStyleBlack(FontFamily(Font(R.font.font_900)))
}