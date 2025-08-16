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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.ansar.autoPartsApp.base.SelectableItem
import com.ansar.autoPartsApp.base.ext.CustomText
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoPartsApp.base.ext.ifBlank
import com.ansar.autoPartsApp.base.ext.top
import com.ansar.autoPartsApp.base.navigation.RootNavigator
import com.ansar.autoPartsApp.domain.model.ProductUI
import com.ansar.autoPartsApp.features.auth.AuthScreen
import com.ansar.autoPartsApp.features.product.ProductScreen
import com.ansar.autoPartsApp.uikit.designe.AutoComplete
import com.ansar.autoPartsApp.uikit.designe.BaseTextFiled
import com.ansar.autoPartsApp.uikit.designe.fixTextOffsetForIOS
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import com.ansar.autoparts.images.AppResourceImages
import io.github.skeptick.libres.compose.painterResource

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val tabNavigator = LocalTabNavigator.current
        val rootNavigator = RootNavigator.currentOrThrow
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
//                                CustomText(
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
//                                CustomText(
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
                                    CustomText(
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
                    Box(Modifier.fillMaxSize()) {
                        CustomText(
                            modifier = Modifier.align(Alignment.Center)
                                .padding(horizontal = 16.dp),
                            text = if (state.emptyText) "По вашему запросу ничего не найдено" else "",
                            style = AppTheme.typography.bold.copy(
                                fontSize = 24.sp,
                                color = AppTheme.colors.text,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                    LazyColumn(
                        state = lazyListState,
                        contentPadding = PaddingValues(vertical = 16.dp),
                    ) {
                        items(state.products) {
                            Item(Modifier.clickableRound(8.dp) {
                                if (viewModel.sessionManager.isAuth) {
                                    navigator.push(ProductScreen(it.id))
                                } else {
                                    rootNavigator.replaceAll(AuthScreen())
                                }

                            }, it)
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
                                contentAlignment = Alignment.Center
                            ) {
                                CustomText(
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
                                contentAlignment = Alignment.Center,
                            ) {
                                CustomText(
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

                        CustomText(
                            modifier = Modifier.padding(top = 16.dp)
                                .padding(horizontal = 8.dp),
                            text = "Фильтр",
                            style = AppTheme.typography.semiBold.copy(
                                fontSize = 18.sp,
                                color = AppTheme.colors.white,
                                textAlign = TextAlign.Center,
                            )
                        )
                        Spacer(Modifier.padding(8.dp))
                        LazyColumn(Modifier.background(AppTheme.colors.mainColor)) {
                            item {
                                AutoComplete(
                                    modifier = Modifier,
                                    expanded = state.catalogSelected,
                                    title = "Каталог",
                                    hint = "Каталог",
                                    list = state.category,
                                    paddingStart = 16.dp,
                                    paddingEnd = 16.dp,
                                    onClick = {
                                        viewModel.onClickCatalog(it)
                                        viewModel.onClickBrand(false)
                                    }
                                ) {
                                    viewModel.selectCategory(it)
                                }
                                AutoComplete(
                                    modifier = Modifier.top(12.dp),
                                    expanded = state.brandSelected,
                                    title = "Отбор по бренду",
                                    hint = "Бренды",
                                    list = state.brand,
                                    paddingStart = 16.dp,
                                    paddingEnd = 16.dp,
                                    onClick = {
                                        viewModel.onClickBrand(it)
                                        viewModel.onClickCatalog(false)
                                    }
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
        CustomText(
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
fun Item(modifier: Modifier, item: ProductUI) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            width = 2.dp,
            color = AppTheme.colors.border
        )
    ) {
        Column {
//            Table(title = "Код", description = item.id.toString().ifBlank { "-" })
//            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
//            Table(title = "Артикул", description = item.article.ifBlank { "-" })
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "OEM", description = item.oem.ifBlank { "-" })
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "Наименование", description = item.title.ifBlank { "-" })
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "Бренд", description = item.brand.title.ifBlank { "-" })
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "ЕИ", description = item.unit.ifBlank { "-" })
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "Цена", description = item.price.ifBlank { "-" })
            if (item.isAvailable == 0) {
                Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
                CustomText(
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
fun Table(title: String, description: String) {
    var size by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Row(modifier = Modifier) {
//            Divider(Modifier.width(1.dp).height(size), color = AppTheme.colors.border)
        Box(
            modifier = Modifier.weight(0.2f)
        ) {
            CustomText(
                modifier = Modifier.padding(horizontal = 4.dp).align(Alignment.CenterStart),
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
            modifier = Modifier.weight(0.4f).onSizeChanged {
                with(density) {
                    size = it.height.toDp()
                }
            }
        ) {
            CustomText(
                modifier = Modifier.padding(horizontal = 4.dp).align(Alignment.CenterStart),
                text = description,
                style = AppTheme.typography.medium.copy(
                    fontSize = 12.sp,
                    color = AppTheme.colors.text,
                ),
            )
        }
//            Divider(Modifier.width(1.dp).height(size), color = AppTheme.colors.border)
    }
}
