package com.ansar.autoPartsApp.base.ext

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

internal fun CenterHorizontally(): Alignment.Horizontal {
    return Alignment.CenterHorizontally
}

internal fun CenterVertically(): Alignment.Vertical {
    return Alignment.CenterVertically
}

internal fun Modifier.horizontal(dp: Dp): Modifier {
    return this.padding(horizontal = dp)
}

internal fun Modifier.vertical(dp: Dp): Modifier {
    return this.padding(vertical = dp)
}

internal fun Modifier.top(dp: Dp): Modifier {
    return this.padding(top = dp)
}

internal fun Modifier.bottom(dp: Dp): Modifier {
    return this.padding(bottom = dp)
}

internal fun Modifier.end(dp: Dp): Modifier {
    return this.padding(end = dp)
}

internal fun Modifier.start(dp: Dp): Modifier {
    return this.padding(start = dp)
}