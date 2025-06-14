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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import com.ansar.autoparts.AppResource
import io.github.skeptick.libres.compose.painterResource

enum class AppTextFiledType {
    TEXT, PASSWORD
}

@Composable
fun BaseTextFiled(
    value: String,
    hint: String,
    shape: Shape = RoundedCornerShape(8.dp),
    type: AppTextFiledType = AppTextFiledType.TEXT,
    left: @Composable (BoxScope.() -> Unit)? = null,
    right: @Composable (BoxScope.() -> Unit)? = null,
    fill: Boolean = true,
    onValueChange: (String) -> Unit
) {
    var _value by remember(value) { mutableStateOf(value) }
    var isFocused by remember { mutableStateOf(false) }
    var visualTransformation by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Card(
        modifier = Modifier.fillMaxWidth().background(AppTheme.colors.white).onSizeChanged {
            with(density) {
                size = it.height.toDp()
            }
        },
        border = if (isFocused) BorderStroke(3.dp, AppTheme.colors.borderClick)
        else BorderStroke(1.dp, AppTheme.colors.border),
        shape = shape
    ) {

        Row(modifier = Modifier.background(AppTheme.colors.white)) {
            BasicTextField(
                modifier = Modifier.fillMaxWidth().background(AppTheme.colors.white).padding(16.dp)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }.weight(1f, fill),
                value = _value,
                onValueChange = {
                    _value = it
                    onValueChange(it)
                },
                textStyle = AppTheme.typography.medium.copy(
                    fontSize = 16.sp,
                    color = AppTheme.colors.text,
                ),
                singleLine = true,
                maxLines = 1,
                decorationBox = { innerTextField ->
                    if (_value.isEmpty() && hint.isNotEmpty()) {
                        androidx.compose.material.Text(
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
                    AppTextFiledType.TEXT -> VisualTransformation.None
                    AppTextFiledType.PASSWORD -> if (visualTransformation) VisualTransformation.None else PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = when (type) {
                        AppTextFiledType.TEXT -> KeyboardType.Text
                        AppTextFiledType.PASSWORD -> KeyboardType.Text
                    },
                    imeAction = ImeAction.Done
                ),
                cursorBrush = SolidColor(AppTheme.colors.text)
            )
            if (type == AppTextFiledType.PASSWORD) {
                Box(modifier = Modifier.height(size).background(AppTheme.colors.white)) {
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