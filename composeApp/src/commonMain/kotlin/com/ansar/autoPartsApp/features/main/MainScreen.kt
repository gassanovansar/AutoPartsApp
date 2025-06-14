package com.ansar.autoPartsApp.features.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.ansar.autoPartsApp.base.SelectableItem
import com.ansar.autoPartsApp.base.ext.clickableRound
import com.ansar.autoPartsApp.uikit.designe.BaseTextFiled
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoPartsApp.uikit.theme.AppTheme

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { MainViewModel() }
        val state = viewModel.stateFlow.collectAsState()
        var showDialog by remember { mutableStateOf(false) }
        var size by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current
        PageContainer(
            isLoading = viewModel.status.collectAsState(false),
            error = viewModel.error.collectAsState(initial = null),
            header = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Card(
                        modifier = Modifier.height(size).clickableRound(8.dp) {
                            showDialog = true
                        },
                        shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                        border = BorderStroke(2.dp, AppTheme.colors.borderClick),
                        backgroundColor = AppTheme.colors.mainColor
                    ) {
                        Box(Modifier.fillMaxHeight()) {
                            Text(
                                modifier = Modifier.align(Alignment.Center)
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                text = "LADA",
                                style = AppTheme.typography.semiBold.copy(
                                    fontSize = 16.sp,
                                    color = AppTheme.colors.white,
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }

                    }
                    Spacer(Modifier.width(8.dp))
                    BaseTextFiled(
                        modifier = Modifier.weight(1f).onSizeChanged {
                            with(density) {
                                size = it.height.toDp()
                            }
                        }, value = "", hint = "", mini = true,
                        shape = RoundedCornerShape(bottomStart = 8.dp, topStart = 8.dp)
                    ) {

                    }
                    Card(
                        modifier = Modifier.height(size).width(size),
                        shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                        border = BorderStroke(1.dp, AppTheme.colors.border),
                        backgroundColor = AppTheme.colors.white
                    ) {}
                    Spacer(Modifier.width(4.dp))
                }
            },
            content = {

            })
        SlideInDialog(
            visible = showDialog,
            onDismissRequest = { showDialog = false },
        )
    }
}

@Composable
fun SlideInDialog(
    onDismissRequest: () -> Unit,
    visible: Boolean
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
        modifier = Modifier.padding(top = 16.dp).clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = {},
        ),
        visible = visible,
        enter = slideInHorizontally() + expandHorizontally() + fadeIn(),
        exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()
    ) {

        Card(
            shape = RoundedCornerShape(4.dp),
            backgroundColor = AppTheme.colors.mainColor
        ) {
            Row(modifier = Modifier.padding(32.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Chip()
                    Chip()
                    Chip()
                    Chip()
                }
            }
        }


    }
}

@Composable
private fun Chip(item: SelectableItem<String> = SelectableItem("LADA", true)) {

    Box(
        Modifier.background(
            if (item.isSelected) AppTheme.colors.white else Color.Transparent,
            shape = RoundedCornerShape(8.dp)
        ),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center).padding(horizontal = 8.dp, vertical = 4.dp),
            text = "LADA",
            style = AppTheme.typography.semiBold.copy(
                fontSize = 16.sp,
                color = if (item.isSelected) AppTheme.colors.text else AppTheme.colors.white,
                textAlign = TextAlign.Center,
            )
        )
    }
}

