package com.ansar.autoPartsApp.uikit.designe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoPartsApp.base.ext.vertical
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import com.ansar.autoparts.images.AppResourceImages
import io.github.skeptick.libres.compose.painterResource
import kotlin.math.max
import kotlin.math.roundToInt

interface DropDown {
    val id: Int
    val title: String
}

@Composable
internal fun AutoComplete(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    list: List<SelectableDropDownItem>,
    paddingStart: Dp = 0.dp,
    paddingEnd: Dp = 0.dp,
    title: String? = null,
    hint: String = "",
    error: Boolean = false,
    errorText: String? = null,
    mini: Boolean = false,
    addWidthDp: Dp = 0.dp,
    onClick: (Boolean) -> Unit = {},
    typeOnClick: (SelectableDropDownItem) -> Unit = {},
) {
    var width by remember { mutableStateOf(0.dp) }
    var search by remember { mutableStateOf("") }
    var allList by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    var _expanded by remember(expanded) { mutableStateOf(expanded) }
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
                        modifier = Modifier.size(18.dp).align(Alignment.CenterVertically)
                            .padding(end = 2.dp),
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
            Column(modifier = Modifier.background(Color.Transparent).onSizeChanged {
                with(density) {
                    width = it.width.toDp() + addWidthDp
                }
            })
            {
                val keyboardController = LocalSoftwareKeyboardController.current
                AppCard(shape =  RoundedCornerShape(8.dp)) {
                    BaseTextFiled(
                        modifier = Modifier.width(200.dp),
                        value = search,
                        hint = hint,
                        mini = true,
                        shape = RoundedCornerShape(8.dp),
                        right = {
                            androidx.compose.material3.IconButton(onClick = {
                                _expanded = !_expanded
                                onClick(_expanded)
                                if (!_expanded) keyboardController?.hide()
                            }) {
                                androidx.compose.material3.Icon(
                                    modifier = Modifier,
                                    painter = if (_expanded) AppResourceImages.arrowupcolor2.painterResource() else AppResourceImages.arrowdowncolor2.painterResource(),
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        },
                        isFocused = {
                            _expanded = it
                        }
                    ) {
                        search = it
                        allList = false
                        _expanded = true
                        onClick(_expanded)
                    }
                }


                AnimatedVisibility(visible = _expanded) {
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
                            LazyColumnWithScrollbar(
                                modifier = Modifier.heightIn(max = 240.dp)
                            ) {
                                itemsIndexed(if (allList) list else list.filter { it.data.title.contains(search, ignoreCase = true) }) { index, it ->
                                    val theme = if (it.isSelected) AppTheme.typography.semiBold else AppTheme.typography.regular
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
                                            style = theme.copy(fontSize = if (mini) 12.sp else 18.sp),
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
@Composable
fun LazyColumnWithScrollbar(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    minThumbHeight: Dp = 20.dp,
    thumbWidth: Dp = 4.dp,
    trackWidth: Dp = 2.dp,
    thumbColor: Color = Color.DarkGray,
    trackColor: Color = Color.LightGray.copy(alpha = 0.5f),
    verticalPadding: Dp = 8.dp,
    content: LazyListScope.() -> Unit
) {
    val density = LocalDensity.current

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth().padding(end = 8.dp)
        ) {
            content()
        }

        val layoutInfo = listState.layoutInfo
        val visibleItems = layoutInfo.visibleItemsInfo
        val totalItems = layoutInfo.totalItemsCount
        val viewportPx = (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset).toFloat()

        if (totalItems > 0 && viewportPx > 0f && visibleItems.isNotEmpty()) {
            val averageItemHeightPx = visibleItems.sumOf { it.size }.toFloat() / visibleItems.size
            val contentHeightPx = averageItemHeightPx * totalItems
            val scrollRangePx = (contentHeightPx - viewportPx).coerceAtLeast(0f)
            val scrollOffsetPx = listState.firstVisibleItemIndex * averageItemHeightPx +
                    listState.firstVisibleItemScrollOffset.toFloat()

            val minThumbPx = with(density) { minThumbHeight.toPx() }
            val thumbHeightPx = max(minThumbPx, (viewportPx * (viewportPx / contentHeightPx)))

            val paddingPx = with(density) { verticalPadding.toPx() }
            val availableScrollAreaPx = viewportPx - paddingPx * 2 - thumbHeightPx

            val thumbOffsetPx = if (scrollRangePx <= 0f) {
                paddingPx
            } else {
                paddingPx + (scrollOffsetPx / scrollRangePx) * availableScrollAreaPx
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .padding(end = 4.dp)
            ) {
                // Дорожка
                Box(
                    modifier = Modifier.padding(vertical = 8.dp)
                        .fillMaxHeight()
                        .width(trackWidth)
                        .background(trackColor, shape = RoundedCornerShape(trackWidth / 2))
                        .align(Alignment.Center)
                )

                // Ползунок
                Box(
                    modifier = Modifier
                        .offset { IntOffset(x = 0, y = thumbOffsetPx.roundToInt()) }
                        .width(thumbWidth)
                        .height(with(density) { thumbHeightPx.toDp() })
                        .background(thumbColor, shape = RoundedCornerShape(thumbWidth / 2))
                        .align(Alignment.TopCenter)
                )
            }
        }
    }
}





