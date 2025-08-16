package com.ansar.autoPartsApp.features.historyDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.ansar.autoPartsApp.base.ext.CustomText
import com.ansar.autoPartsApp.domain.model.CartUI
import com.ansar.autoPartsApp.domain.model.OrderUI
import com.ansar.autoPartsApp.features.main.Table
import com.ansar.autoPartsApp.uikit.designe.toolBar.Toolbar
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoPartsApp.uikit.theme.AppTheme

class HistoryDetailScreen(private val item: OrderUI? = null) : Screen {
    @Composable
    override fun Content() {
        PageContainer(
            header = {
                Toolbar(title = item?.id.toString(), line = true)
            }, content = {

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(item?.cart.orEmpty()) {

                        Item(it)
                    }
                }
            }, footer = {

                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(1.dp)
                            .background(AppTheme.colors.mainColor)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                            .padding(vertical = 12.dp)
                    ) {
                        CustomText(
                            modifier = Modifier.align(Alignment.CenterVertically).fillMaxWidth()
                                .weight(1f),
                            text = "Общая сумма",
                            style = AppTheme.typography.medium.copy(
                                fontSize = 16.sp,
                                color = AppTheme.colors.text
                            )
                        )
                        CustomText(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = item?.price.toString(),
                            style = AppTheme.typography.medium.copy(
                                fontSize = 18.sp,
                                color = AppTheme.colors.text,
                            )
                        )
                    }

                }

            })

    }


    @Composable
    fun Item(item: CartUI) {
        Card(
            shape = RoundedCornerShape(0.dp),
            border = BorderStroke(
                width = 2.dp,
                color = AppTheme.colors.border
            )
        ) {
            Column {
//            Table(title = "Код", description = item.product.id.toString().ifBlank { "-" })
//            Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
//            Table(title = "Артикул", description = item.product.article.ifBlank { "-" })
                Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
                Table(title = "OEM", description = item.product.oem.ifBlank { "-" })
                Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
                Table(
                    title = "Наименование",
                    description = item.product.title.ifBlank { "-" }
                )
                Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
                Table(title = "Бренд", description = item.product.brand.title.ifBlank { "-" })
                Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
                Table(title = "Цена", description = item.product.price.ifBlank { "-" })
                Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
                Table(title = "Cумма", description = item.product.price.ifBlank { "-" })
                Divider(Modifier.fillMaxWidth(), color = AppTheme.colors.border)
                Table(title = "Количество", description = "${item.count} ${item.product.unit}")
            }

        }
    }
}
