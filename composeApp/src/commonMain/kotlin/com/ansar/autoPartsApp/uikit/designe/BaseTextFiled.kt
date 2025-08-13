package com.ansar.autoPartsApp.uikit.designe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ansar.autoPartsApp.base.ext.CustomText
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import com.ansar.autoparts.AppResource
import io.github.skeptick.libres.compose.painterResource

enum class AppTextFiledType {
    TEXT, PASSWORD, DIGIT
}

@Composable
fun BaseTextFiled(
    modifier: Modifier = Modifier,
    value: String,
    hint: String = "",
    shape: Shape = RoundedCornerShape(8.dp),
    type: AppTextFiledType = AppTextFiledType.TEXT,
    left: @Composable (BoxScope.() -> Unit)? = null,
    right: @Composable (BoxScope.() -> Unit)? = null,
    fill: Boolean = true,
    error: Boolean = false,
    mini: Boolean = false,
    isFocused: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit
) {
    var _value by remember(value) { mutableStateOf(value) }
    var _error by remember(error) { mutableStateOf(error) }
    var isFocused by remember { mutableStateOf(false) }
    var visualTransformation by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val errorColor = if (_error) AppTheme.colors.error.copy(alpha = 0.3f) else AppTheme.colors.white
    Card(
        modifier = modifier.fillMaxWidth().background(errorColor).onSizeChanged {
            with(density) {
                size = it.height.toDp()
            }
        },
        border = if (isFocused) BorderStroke(3.dp, AppTheme.colors.borderClick)
        else BorderStroke(1.dp, AppTheme.colors.border),
        shape = shape
    ) {
        Row(modifier = Modifier.background(errorColor)) {
            BasicTextField(
                modifier = Modifier.fillMaxWidth().background(errorColor)
                    .padding(if (mini) 8.dp else 16.dp)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                        isFocused(focusState.isFocused)
                    }.weight(1f, fill),
                value = _value,
                onValueChange = {
                    if (type == AppTextFiledType.DIGIT) {
                        val filtered = it.filter { it.isDigit() }
                        _value = filtered
                        onValueChange(filtered)
                    } else {
                        _value = it
                        onValueChange(it)
                    }

                },
                textStyle = AppTheme.typography.medium.copy(
                    fontSize = 16.sp,
                    color = AppTheme.colors.text,
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None,
                    )
                ),
                singleLine = true,
                maxLines = 1,
                decorationBox = { innerTextField ->
                    if (_value.isEmpty() && hint.isNotEmpty()) {
                        CustomText(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = hint,
                            style = AppTheme.typography.medium.copy(
                                fontSize = 16.sp,
                                color = AppTheme.colors.hint,
                            ),
                            maxLines = 1
                        )
                    }
                    innerTextField()
                },
                visualTransformation = when (type) {
                    AppTextFiledType.TEXT, AppTextFiledType.DIGIT -> VisualTransformation.None
                    AppTextFiledType.PASSWORD -> if (visualTransformation) VisualTransformation.None else PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = when (type) {
                        AppTextFiledType.TEXT -> KeyboardType.Text
                        AppTextFiledType.PASSWORD -> KeyboardType.Text
                        AppTextFiledType.DIGIT -> KeyboardType.Number
                    },
                    imeAction = ImeAction.Done
                ),
                cursorBrush = SolidColor(AppTheme.colors.text)
            )
            if (type == AppTextFiledType.PASSWORD) {
                Box(modifier = Modifier.height(size).background(errorColor)) {
                    Image(
                        painter = if (!visualTransformation) AppResource.image.passwordoff.painterResource() else AppResource.image.passwordon.painterResource(),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 10.dp).align(
                            Alignment.Center
                        ).clickableRound(8.dp) {
                            visualTransformation = !visualTransformation
                        },
                    )
                }


            } else
                Box(modifier = Modifier.height(size)) {
                    right?.invoke(this)
                }
        }
    }
}