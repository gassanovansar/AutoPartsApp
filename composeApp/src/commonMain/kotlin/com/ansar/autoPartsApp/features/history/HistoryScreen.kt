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
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoPartsApp.base.ext.top
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
//        LaunchedEffect(key1 = shouldStartPaginate.value) {
//            if (canLoad && shouldStartPaginate.value && !viewModel.status.value)
////                viewModel.loadProducts()
//        }


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
        }

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
            Table(title = "Цена", description = "5 308")
            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
            Table(title = "Количество", description = "10 шт")
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
