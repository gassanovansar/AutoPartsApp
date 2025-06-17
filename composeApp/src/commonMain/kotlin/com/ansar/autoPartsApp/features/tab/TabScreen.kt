package com.ansar.autoPartsApp.features.tab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent
import kotlin.random.Random

class TabScreen(private val tab: Tab = MainTabScreen) : Screen, KoinComponent {

    override val key: ScreenKey = Random.nextInt().toString()


    companion object {
        val hiddenBottom = MutableStateFlow(false)
    }

    @Composable
    override fun Content() {
//        val screenModel = rememberScreenModel { TabScreenModel() }
        TabNavigator(
            tab = tab,
            disposeNestedNavigators = true
        ) {
            PageContainer(
                content = {
                    it.current.Content()
                },
                footer = {

                    val hiddenBottom by hiddenBottom.collectAsState()
                    AnimatedVisibility(
                        !hiddenBottom, enter = expandVertically(
                            animationSpec = keyframes {
                                this.durationMillis = 50
                            }
                        ),
                        exit = shrinkVertically(animationSpec = keyframes {
                            this.durationMillis = 50
                        })
                    ) {
                        Column {
                            Divider(
                                Modifier.fillMaxWidth().height(1.dp)
                                    .background(AppTheme.colors.mainColor)
                            )
                            Row(
                                modifier = Modifier
                                    .background(AppTheme.colors.mainColor)
                            ) {
                                TabNavItem(MainTabScreen)
                                TabNavItem(HistoryTabScreen)
//                            TabNavItem(ProfileTabScreen)
//                            val role by screenModel.sessionManager.role.collectAsState()
//                            when (role) {
//                                Role.USER -> {
//                                    TabNavItem(PurchasesTabScreen)
//                                    TabNavItem(FavouriteTabScreen)
//                                }
//
//                                Role.CAPPER -> {
//                                    TabNavItem(AddForecastTabScreen)
////                                    {
////                                        it.push(SubscriptionScreen(SubscriptionNav.CREATE_FORECAST))
////                                    }
//                                    TabNavItem(MySalesTabScreen)
//                                }
//                            }

//                            TabNavItem(MenuTabScreen)
                            }

                        }

                    }

                }
            )
        }
    }

    @Composable
    private fun RowScope.TabNavItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current
        val navigator = LocalNavigator.currentOrThrow
        val selected = tabNavigator.current == tab
        val color = if (selected) AppTheme.colors.white else AppTheme.colors.mainColor
        val background = if (selected) AppTheme.colors.mainColor else AppTheme.colors.white
        val textStyle = if (selected) AppTheme.typography.semiBold else AppTheme.typography.regular

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {
                tabNavigator.current = tab
            }.background(background).padding(vertical = 8.dp)
                .weight(1f)
        ) {
            tab.options.icon?.let {
                Box {
                    Image(
                        painter = it,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color),
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(24.dp)
                    )
                }
            }
            Text(
                text = tab.options.title,
                modifier = Modifier,
                color = color,
                style = textStyle.copy(fontSize = 12.sp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }
    }
}

