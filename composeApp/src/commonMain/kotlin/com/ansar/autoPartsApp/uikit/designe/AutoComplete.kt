package com.ansar.autoPartsApp.uikit.designe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CheckboxColors
import androidx.compose.material3.Checkbox
import androidx.compose.material.Chip
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.base.ext.bottom
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import com.ansar.autoparts.images.AppResourceImages
import io.github.skeptick.libres.compose.painterResource

interface DropDown {
    val id: Int
    val title: String
}

@Composable
internal fun AutoComplete(
    modifier: Modifier = Modifier,
    list: List<SelectableDropDownItem>,
    paddingStart: Dp = 0.dp,
    paddingEnd: Dp = 0.dp,
    title: String? = null,
    hint: String = "",
    error: Boolean = false,
    errorText: String? = null,
    mini: Boolean = false,
    addWidthDp: Dp = 0.dp,
    typeOnClick: (SelectableDropDownItem) -> Unit = {},
) {
    var width by remember { mutableStateOf(0.dp) }
    var search by remember { mutableStateOf("") }
    var allList by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    var expanded by remember { mutableStateOf(false) }
    Box(modifier) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(list.filter { it.isSelected }) {
                Row(
                    Modifier.background(
                        AppTheme.colors.white,
                        shape = RoundedCornerShape(16.dp)
                    ).clickableRound(16.dp) {
                        typeOnClick(it)
                    },
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 2.dp),
                        text = it.data.title,
                        style = AppTheme.typography.semiBold.copy(
                            fontSize = 12.sp,
                            color = AppTheme.colors.text,
                            textAlign = TextAlign.Center,
                        )
                    )
                    Image(
                        modifier = Modifier.size(18.dp).align(Alignment.CenterVertically).padding(end = 2.dp),
                        painter = painterResource(AppResourceImages.close),
                        contentDescription = null
                    )
                }
            }
        }

        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = AppTheme.colors.mainColor,
            modifier = Modifier.padding(top = 32.dp).padding(start = paddingStart, end = paddingEnd)
        ) {
            Column(modifier = Modifier.background(AppTheme.colors.mainColor).onSizeChanged {
                with(density) {
                    width = it.width.toDp() + addWidthDp
                }
            })
            {
                val keyboardController = LocalSoftwareKeyboardController.current
                BaseTextFiled(
                    modifier = Modifier.width(200.dp),
                    value = search,
                    hint = hint,
                    mini = true,
                    shape = RoundedCornerShape(8.dp),
                    right = {
                        androidx.compose.material3.IconButton(onClick = {
                            expanded = !expanded
                            if (!expanded) keyboardController?.hide()
                        }) {
                            androidx.compose.material3.Icon(
                                modifier = Modifier,
                                painter = if (expanded) AppResourceImages.arrowupcolor2.painterResource() else AppResourceImages.arrowdowncolor2.painterResource(),
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    },
                    isFocused = {
                            expanded = it
                    }
                ) {
                    search = it
                    allList = false
                    expanded = true
                }

                AnimatedVisibility(visible = expanded) {
                    AppCard(
                        modifier = Modifier.width(200.dp).padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp),
                        border = BorderStroke(1.dp, AppTheme.colors.border),
                        elevation = 1.dp
                    ) {
                        if (list.isEmpty()) {
                            Text(
                                style = AppTheme.typography.regular.copy(
                                    fontSize = if (mini) 12.sp else 18.sp,
                                ),
                                text = "Список пуст",
                                modifier = Modifier.fillMaxWidth()
                                    .padding(if (mini) 6.dp else 10.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.heightIn(max = 150.dp),
                            ) {
                                itemsIndexed(if (allList) {
                                    list
                                } else {
                                    list.filter {
                                        it.data.title.contains(
                                            search,
                                            ignoreCase = true
                                        )
                                    }
                                }) { index, it ->
                                    val theme =
                                        if (it.isSelected) AppTheme.typography.semiBold else AppTheme.typography.regular
                                    Row(Modifier.clickable {
                                        allList = true
                                        keyboardController?.hide()
                                        typeOnClick(it)
                                    }) {
                                        Checkbox(
                                            it.isSelected,
                                            { _ ->
                                                allList = true
                                                keyboardController?.hide()
                                                typeOnClick(it)
                                            },
                                            colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.mainColor)
                                        )
                                        Text(
                                            style = theme.copy(
                                                fontSize = if (mini) 12.sp else 18.sp,
                                            ),
                                            text = it.data.title,
                                            modifier = Modifier.fillMaxWidth()
                                                .align(Alignment.CenterVertically),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    if (index + 1 != list.size)
                                        Divider(
                                            color = AppTheme.colors.border,
                                            modifier = Modifier.padding(horizontal = 5.dp)
                                        )
                                }
                            }
                        }


                    }
                }
            }
        }

        AnimatedVisibility(error && errorText != null) {
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = errorText.toString(),
                style = AppTheme.typography.regular.copy(
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    color = AppTheme.colors.red,
                )
            )
        }
    }

}