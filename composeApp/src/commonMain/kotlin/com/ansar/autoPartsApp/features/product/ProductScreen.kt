package com.ansar.autoPartsApp.features.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoPartsApp.features.main.Item
import com.ansar.autoPartsApp.uikit.designe.AppTextFiledType
import com.ansar.autoPartsApp.uikit.designe.BaseTextFiled
import com.ansar.autoPartsApp.uikit.designe.PrimaryButton
import com.ansar.autoPartsApp.uikit.designe.toolBar.BackIcon
import com.ansar.autoPartsApp.uikit.designe.toolBar.Toolbar
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import com.ansar.autoparts.images.AppResourceImages
import io.github.skeptick.libres.compose.painterResource

class ProductScreen(private val id: Int) : Screen {

    @Composable
    override fun Content() {
        var size by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current
        val viewModel = rememberScreenModel { ProductViewModel() }
        val state by viewModel.stateFlow.collectAsState()
        LaunchedEffect(viewModel) {
            viewModel.getProduct(id)
        }

        PageContainer(
            isLoading = viewModel.status.collectAsState(false),
            error = viewModel.error.collectAsState(initial = null),
            header = {
                Toolbar(leftIcon = { BackIcon() }, line = true)
            }, content = {
                Column(modifier = Modifier.fillMaxSize()) {
                    Item(Modifier.padding(16.dp), state.product)

                    Row(modifier = Modifier.align(Alignment.End).padding(horizontal = 16.dp)) {
                        Box(
                            Modifier.background(
                                AppTheme.colors.mainColor,
                                shape = RoundedCornerShape(8.dp)
                            ).size(size).clickableRound(8.dp) {
                                viewModel.removeCount()
                            }
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize().align(Alignment.Center)
                                    .padding(8.dp),
                                painter = AppResourceImages.minus.painterResource(),
                                contentDescription = null
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        BaseTextFiled(
                            value = state.count,
                            modifier = Modifier.weight(1f).onSizeChanged {
                                with(density) {
                                    size = it.height.toDp()
                                }
                            },
                            type = AppTextFiledType.DIGIT
                        ) {
                            val filtered = it.filter { it.isDigit() }
                            viewModel.changeCount(filtered)
                        }
                        Spacer(Modifier.width(16.dp))
                        Box(
                            Modifier.background(
                                AppTheme.colors.mainColor,
                                shape = RoundedCornerShape(8.dp)
                            ).size(size).clickableRound(0.dp) {
                                viewModel.addCount()
                            }
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize().align(Alignment.Center)
                                    .padding(8.dp),
                                painter = AppResourceImages.plus.painterResource(),
                                contentDescription = null
                            )
                        }
                    }
                    PrimaryButton(
                        text = "Заказать",
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        enabled = state.product.isAvailable > 0
                    ) {
                        viewModel.createOrder(id)
                    }
                }
            })
    }
}