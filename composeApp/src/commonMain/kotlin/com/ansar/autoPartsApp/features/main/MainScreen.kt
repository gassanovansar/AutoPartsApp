package com.ansar.autoPartsApp.features.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ansar.autoPartsApp.base.SelectableItem
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoPartsApp.base.ext.top
import com.ansar.autoPartsApp.features.product.ProductScreen
import com.ansar.autoPartsApp.uikit.designe.AutoComplete
import com.ansar.autoPartsApp.uikit.designe.BaseTextFiled
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import com.ansar.autoparts.images.AppResourceImages
import io.github.skeptick.libres.compose.painterResource

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { MainViewModel() }
        val state by viewModel.stateFlow.collectAsState()
        var showLeftDialog by remember { mutableStateOf(false) }
        var showRightDialog by remember { mutableStateOf(false) }
        var size by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current

        val canLoad by viewModel.canLoad.collectAsState()
        val lazyListState = rememberLazyListState()
        val shouldStartPaginate = remember {
            derivedStateOf {
                canLoad && (lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: -9) >= (lazyListState.layoutInfo.totalItemsCount - 6)
            }
        }
        LaunchedEffect(key1 = shouldStartPaginate.value) {
            if (canLoad && shouldStartPaginate.value && !viewModel.status.value)
                viewModel.loadProducts()
        }


        Box(modifier = Modifier.fillMaxSize()) {
            PageContainer(
                isLoading = viewModel.status.collectAsState(false),
                error = viewModel.error.collectAsState(initial = null),
                header = {
                    Column {
//                    Row(modifier = Modifier.padding(top = 8.dp)) {
//                        Card(
//                            modifier = Modifier.height(size).clickableRound(8.dp) {
//                                showDialog = true
//                            },
//                            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
//                            border = BorderStroke(2.dp, AppTheme.colors.borderClick),
//                            backgroundColor = AppTheme.colors.mainColor
//                        ) {
//                            Box(Modifier.fillMaxHeight()) {
//                                Text(
//                                    modifier = Modifier.align(Alignment.Center)
//                                        .padding(horizontal = 8.dp, vertical = 4.dp),
//                                    text = "Заводской каталог",
//                                    style = AppTheme.typography.semiBold.copy(
//                                        fontSize = 12.sp,
//                                        color = AppTheme.colors.white,
//                                        textAlign = TextAlign.Center,
//                                    )
//                                )
//                            }
//
//                        }
//
//                        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
//                            Box(
//                                Modifier.size(32.dp).align(Alignment.Center)
//                                    .background(AppTheme.colors.mainColor)
//                            )
//                        }
//                        Card(
//                            modifier = Modifier.height(size).clickableRound(8.dp) {
//                                showDialog = true
//                            },
//                            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
//                            border = BorderStroke(2.dp, AppTheme.colors.borderClick),
//                            backgroundColor = AppTheme.colors.mainColor
//                        ) {
//                            Box(Modifier.fillMaxHeight()) {
//                                Text(
//                                    modifier = Modifier.align(Alignment.Center)
//                                        .padding(horizontal = 8.dp, vertical = 4.dp),
//                                    text = "Отбор по бренду",
//                                    style = AppTheme.typography.semiBold.copy(
//                                        fontSize = 12.sp,
//                                        color = AppTheme.colors.white,
//                                        textAlign = TextAlign.Center,
//                                    )
//                                )
//                            }
//
//                        }
//
//
//                    }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Card(
                                modifier = Modifier.height(size).width(80.dp).clickableRound(
                                    clip = RoundedCornerShape(
                                        topEnd = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                ) {
                                    showLeftDialog = true
                                },
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                                border = BorderStroke(2.dp, AppTheme.colors.borderClick),
                                backgroundColor = AppTheme.colors.mainColor
                            ) {
                                Box(Modifier.fillMaxHeight()) {
                                    Text(
                                        modifier = Modifier.align(Alignment.Center)
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        text = state.model.find { it.isSelected }?.data?.title.orEmpty(),
                                        style = AppTheme.typography.semiBold.copy(
                                            fontSize = 16.sp,
                                            color = AppTheme.colors.white,
                                            textAlign = TextAlign.Center,
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                            }
                            Spacer(Modifier.width(8.dp))
                            BaseTextFiled(
                                modifier = Modifier.fillMaxWidth().weight(1f).onSizeChanged {
                                    with(density) {
                                        size = it.height.toDp()
                                    }
                                }, value = state.search, hint = "Наименование", mini = true,
                                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                            ) {
                                viewModel.changeSearch(it)
                            }
//                            Card(
//                                modifier = Modifier.height(size).clickableRound(8.dp) {
//                                    showRightDialog = true
//                                },
//                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
//                                border = BorderStroke(1.dp, AppTheme.colors.border),
//                                backgroundColor = AppTheme.colors.mainColor
//                            ) {
//                                Box(Modifier.fillMaxHeight().width(size)) {
//                                    Image(
//                                        modifier = Modifier.align(Alignment.Center),
//                                        painter = painterResource(AppResourceImages.filter),
//                                        contentDescription = null
//                                    )
//                                }
//
//                            }

                            Card(
                                modifier = Modifier.height(size).width(size).clickableRound(
                                    clip = RoundedCornerShape(
                                        topEnd = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                ) {
                                    showRightDialog = true
                                },
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                                border = BorderStroke(1.dp, AppTheme.colors.border),
                                backgroundColor = AppTheme.colors.white
                            ) {
                                Box(Modifier.fillMaxHeight().width(size)) {
                                    Image(
                                        modifier = Modifier.align(Alignment.Center),
                                        painter = painterResource(AppResourceImages.filter),
                                        contentDescription = null
                                    )
                                }
                            }
                            Spacer(Modifier.width(8.dp))

                        }
                    }

                },
                content = {
                    LazyColumn(
                        state = lazyListState,
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.products) {
                            Item(Modifier.clickableRound(8.dp) {
                                navigator.push(ProductScreen(it.id))
                            }, false)
                        }
                    }
                })
            SlideInDialog(
                visible = showLeftDialog,
                onDismissRequest = {
                    showLeftDialog = false
                },
                enter = expandHorizontally() + fadeIn(),
                exit = shrinkHorizontally() + fadeOut(),
            ) {
                Card(
                    shape = RoundedCornerShape(4.dp),
                    backgroundColor = AppTheme.colors.mainColor
                ) {
                    Row(modifier = Modifier.padding(32.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            state.model.forEach {
                                Chip(it.data.title, it.isSelected) {
                                    viewModel.changeModel(it)
                                    showLeftDialog = false
                                }
                            }
                        }
                    }
                }
            }
            SlideInDialog(
                modifier = Modifier.align(Alignment.TopEnd),
                visible = showRightDialog,
                onDismissRequest = {
                    viewModel.products()
                    showRightDialog = false
                },
                enter = expandHorizontally(
                    expandFrom = Alignment.Start
                ) + slideInHorizontally() + fadeIn(),
                exit = shrinkHorizontally(
                    shrinkTowards = Alignment.Start
                ) + slideOutHorizontally() + fadeOut(),
            ) {

                Card(
                    modifier = Modifier.fillMaxHeight().size(232.dp),
                    shape = RoundedCornerShape(4.dp),
                    backgroundColor = AppTheme.colors.mainColor
                ) {
                    Column(modifier = Modifier) {
                        Box(Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                            Box(
                                Modifier.padding(top = 8.dp).background(
                                    AppTheme.colors.white,
                                    shape = RoundedCornerShape(8.dp)
                                ).clickableRound(8.dp) {
                                    showRightDialog = false
                                    viewModel.products()
                                },
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center)
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    text = "Готово",
                                    style = AppTheme.typography.semiBold.copy(
                                        fontSize = 12.sp,
                                        color = AppTheme.colors.text,
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                            Box(
                                Modifier.padding(top = 8.dp).align(Alignment.TopEnd).background(
                                    AppTheme.colors.white,
                                    shape = RoundedCornerShape(8.dp)
                                ).clickableRound(8.dp) {
                                    viewModel.clearFilter()
                                },
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center)
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    text = "Сбросить",
                                    style = AppTheme.typography.semiBold.copy(
                                        fontSize = 12.sp,
                                        color = AppTheme.colors.text,
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                        }

                        LazyColumn(Modifier.background(AppTheme.colors.mainColor)) {

                            item {
                                AutoComplete(
                                    modifier = Modifier.top(32.dp),
                                    title = "Каталог",
                                    hint = "Каталог",
                                    list = state.category,
                                    paddingStart = 16.dp,
                                    paddingEnd = 16.dp
                                ) {
                                    viewModel.selectCategory(it)
                                }
                                AutoComplete(
                                    modifier = Modifier.top(12.dp),
                                    title = "Отбор по бренду",
                                    hint = "Бренды",
                                    list = state.brand,
                                    paddingStart = 16.dp,
                                    paddingEnd = 16.dp
                                ) {
                                    viewModel.selectBrand(it)
                                }
                            }
                        }
                    }


                }
            }
        }

    }


}

@Composable
fun SlideInDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    visible: Boolean,
    enter: EnterTransition = fadeIn() + expandIn(),
    exit: ExitTransition = shrinkOut() + fadeOut(),
    content: @Composable() (BoxScope.() -> Unit)
) {
    if (visible) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onDismissRequest,
                )
        )
    }
    AnimatedVisibility(
        modifier = modifier.padding(top = 16.dp).clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = {},
        ),
        visible = visible,
        enter = enter,
        exit = exit
    ) {
        Box {
            content()
        }


    }
}

@Composable
private fun Chip(title: String, selected: Boolean, onClick: () -> Unit) {

    Box(
        Modifier.background(
            if (selected) AppTheme.colors.white else Color.Transparent,
            shape = RoundedCornerShape(8.dp)
        ).clickableRound(8.dp) {
            onClick()
        },
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center).padding(horizontal = 8.dp, vertical = 4.dp),
            text = title,
            style = AppTheme.typography.semiBold.copy(
                fontSize = 16.sp,
                color = if (selected) AppTheme.colors.text else AppTheme.colors.white,
                textAlign = TextAlign.Center,
            )
        )
    }
}


@Composable
fun Item(modifier: Modifier, inStock: Boolean) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            width = 2.dp,
            color = AppTheme.colors.border
        )
    ) {
        Column {
            Table(title = "Код", description = "00-00104437")
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "Артикул", description = "W343405SA")
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "OEM", description = "-")
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(
                title = "Наименование",
                description = "Болт М12 / 1,5 / 48.5/31 (шпилька), JN-821FD Mazda, Hyundai, Kia, Ford"
            )
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "Бренд", description = "WINKOD")
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "ЕИ", description = "шт")
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "Цена", description = "5 308")
            if (!inStock) {
                Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Нет в наличии",
                    style = AppTheme.typography.bold.copy(
                        fontSize = 12.sp,
                        color = AppTheme.colors.red,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }

    }
}

@Composable
private fun Table(title: String, description: String) {
    var size by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Row(modifier = Modifier) {
//            Divider(Modifier.width(1.dp).height(size), color = AppTheme.colors.border)
        Box(
            modifier = Modifier.weight(0.2f).onSizeChanged {
                with(density) {
                    size = it.height.toDp()
                }
            }
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = title,
                style = AppTheme.typography.bold.copy(
                    fontSize = 12.sp,
                    color = AppTheme.colors.text,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Divider(Modifier.width(1.dp).height(size), color = AppTheme.colors.border)
        Box(
            modifier = Modifier.weight(0.3f)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = description,
                style = AppTheme.typography.medium.copy(
                    fontSize = 12.sp,
                    color = AppTheme.colors.text,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
//            Divider(Modifier.width(1.dp).height(size), color = AppTheme.colors.border)
    }
}
