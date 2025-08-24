package com.ansar.autoPartsApp.features.basket

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.ansar.autoPartsApp.base.ext.CustomText
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoPartsApp.domain.model.CartUI
import com.ansar.autoPartsApp.features.main.Table
import com.ansar.autoPartsApp.uikit.designe.PrimaryButton
import com.ansar.autoPartsApp.uikit.designe.toolBar.Toolbar
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import com.ansar.autoparts.images.AppResourceImages
import io.github.skeptick.libres.compose.painterResource

class BasketScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { BasketViewModel() }
        val state by viewModel.stateFlow.collectAsState()
        PageContainer(
            isLoading = viewModel.status.collectAsState(false),
            error = viewModel.error.collectAsState(initial = null),
            header = {
                Toolbar(title = "Корзина", line = true, rightIcon = {
                    Image(
                        modifier = Modifier.padding(8.dp).clickableRound(2.dp) {
                            viewModel.clean()
                        },
                        painter = AppResourceImages.delete.painterResource(),
                        contentDescription = null
                    )
                })
            }, content = {

                Box(Modifier.fillMaxSize()) {
                    CustomText(
                        modifier = Modifier.align(Alignment.Center)
                            .padding(horizontal = 16.dp),
                        text = if (state.emptyText) "Корзина пуста." else "",
                        style = AppTheme.typography.bold.copy(
                            fontSize = 24.sp,
                            color = AppTheme.colors.text,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.cart) {
                        CartItem(Modifier.clickableRound(8.dp) {
//                                navigator.push(ProductScreen(it.id))
                        }, it, add = { viewModel.addCount(it.product.id) }, remove = {
                            viewModel.removeCount(it.product.id)
                        }, delete = {
                            viewModel.delete(it.product.id)
                        })
                    }
                }
            }, footer = {
                if (state.cart.isNotEmpty()) {
                    Column {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(1.dp)
                                .background(AppTheme.colors.mainColor)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                                .padding(top = 12.dp)
                        ) {
                            CustomText(
                                modifier = Modifier.align(Alignment.CenterVertically).fillMaxWidth().weight(1f),
                                text = "Общая сумма",
                                style = AppTheme.typography.medium.copy(
                                    fontSize = 16.sp,
                                    color = AppTheme.colors.text
                                )
                            )
                            CustomText(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = state.price.toString(),
                                style = AppTheme.typography.medium.copy(
                                    fontSize = 18.sp,
                                    color = AppTheme.colors.text,
                                )
                            )
                        }

                        PrimaryButton(
                            text = "Заказать",
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                        ) {
                            viewModel.createTransaction()
                        }

                    }
                }

            })

    }

    @Composable
    fun CartItem(
        modifier: Modifier,
        item: CartUI,
        add: () -> Unit,
        remove: () -> Unit,
        delete: () -> Unit
    ) {

        Card(
            modifier = modifier,
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(
                width = 2.dp,
                color = AppTheme.colors.border
            )
        ) {
            Column {

                Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
                Table(title = "Наименование", description = item.product.title.ifBlank { "-" })
                Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
                Table(title = "Цена", description = item.product.price.toString().ifBlank { "-" })
                Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
                Row(modifier = Modifier.align(Alignment.End).padding(8.dp)) {
                    Box(
                        Modifier.background(
                            AppTheme.colors.mainColor,
                            shape = RoundedCornerShape(8.dp)
                        ).size(32.dp).clickableRound(8.dp) {
                            remove()
                        }
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize().align(Alignment.Center)
                                .padding(4.dp),
                            painter = AppResourceImages.minus.painterResource(),
                            contentDescription = null
                        )
                    }
                    CustomText(
                        modifier = Modifier.width(64.dp).align(Alignment.CenterVertically),
                        text = item.count.toString() + "шт",
                        style = AppTheme.typography.medium.copy(
                            fontSize = 18.sp,
                            color = AppTheme.colors.text,
                            textAlign = TextAlign.Center
                        )
                    )
                    Box(
                        Modifier.background(
                            AppTheme.colors.mainColor,
                            shape = RoundedCornerShape(8.dp)
                        ).size(32.dp).clickableRound(0.dp) {
                            add()
                        }
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize().align(Alignment.Center)
                                .padding(4.dp),
                            painter = AppResourceImages.plus.painterResource(),
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Box(
                        Modifier.background(
                            AppTheme.colors.red,
                            shape = RoundedCornerShape(8.dp)
                        ).size(32.dp).clickableRound(0.dp) {
                            delete()
                        }
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize().align(Alignment.Center)
                                .padding(8.dp),
                            painter = AppResourceImages.delete.painterResource(),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(AppTheme.colors.white)
                        )
                    }
                }
            }
        }

    }

}
