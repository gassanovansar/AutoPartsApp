package com.ansar.autoPartsApp.features.history

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
import com.ansar.autoPartsApp.base.ext.CustomText
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoPartsApp.base.ext.ifBlank
import com.ansar.autoPartsApp.base.ext.top
import com.ansar.autoPartsApp.domain.model.OrderUI
import com.ansar.autoPartsApp.features.historyDetail.HistoryDetailScreen
import com.ansar.autoPartsApp.features.product.ProductScreen
import com.ansar.autoPartsApp.uikit.designe.AutoComplete
import com.ansar.autoPartsApp.uikit.designe.BaseTextFiled
import com.ansar.autoPartsApp.uikit.designe.toolBar.Toolbar
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import com.ansar.autoparts.images.AppResourceImages
import io.github.skeptick.libres.compose.painterResource

class HistoryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { HistoryViewModel() }
        val state by viewModel.stateFlow.collectAsState()

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
                viewModel.loadOrders()
        }
        LaunchedEffect(Unit){
            viewModel.orders()
        }


        Box(modifier = Modifier.fillMaxSize()) {
            PageContainer(
                isLoading = viewModel.status.collectAsState(false),
                error = viewModel.error.collectAsState(initial = null),
                header = {
                    Column {
                        Toolbar(title = "История", line = true)
                    }

                },
                content = {
                    Box(Modifier.fillMaxSize()) {
                        CustomText(
                            modifier = Modifier.align(Alignment.Center)
                                .padding(horizontal = 16.dp),
                            text = if (state.emptyText) "Список заказов пуст." else "",
                            style = AppTheme.typography.bold.copy(
                                fontSize = 24.sp,
                                color = AppTheme.colors.text,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                    LazyColumn(
                        state = lazyListState,
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.orders) {
                            Item(Modifier.clickableRound(8.dp) {
                                navigator.push(HistoryDetailScreen(it))
                            }, it)
                        }
                    }

                })
        }

    }
}

@Composable
fun Item(modifier: Modifier, item: OrderUI) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
        border = BorderStroke(
            width = 2.dp,
            color = AppTheme.colors.border
        )
    ) {
        Column {
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "Транзакция", description = item.id.toString().ifBlank { "-" })
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "Статус", description = item.status.ifBlank { "-" })
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "Общая сумма", description = item.price.ifBlank { "-" })
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
            modifier = Modifier.weight(0.2f)
        ) {
            CustomText(
                modifier = Modifier.padding(horizontal = 4.dp).align(Alignment.CenterStart),
                text = title,
                style = AppTheme.typography.bold.copy(
                    fontSize = 12.sp,
                    color = AppTheme.colors.text,
                ),
            )
        }
        Divider(Modifier.width(1.dp).height(size), color = AppTheme.colors.border)
        Box(
            modifier = Modifier.weight(0.3f).onSizeChanged {
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
