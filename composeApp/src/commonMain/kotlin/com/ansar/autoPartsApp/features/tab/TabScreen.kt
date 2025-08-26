package com.ansar.autoPartsApp.features.tab

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.ansar.autoPartsApp.base.ext.CustomText
import com.ansar.autoPartsApp.base.navigation.RootNavigator
import com.ansar.autoPartsApp.features.auth.AuthScreen
import com.ansar.autoPartsApp.uikit.screens.PageContainer
import com.ansar.autoPartsApp.uikit.theme.AppTheme
import kotlin.random.Random

class TabScreen(private val tab: Tab = MainTabScreen) : Screen {

    override val key: ScreenKey = Random.nextInt().toString()


    @Composable
    override fun Content() {
        TabNavigator(
            tab = tab,
            disposeNestedNavigators = false
        ) {
            PageContainer(
                content = {
                    it.current.Content()
                },
                footer = {


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
                            TabNavItem(BasketTabScreen) {
                                it.replaceAll(AuthScreen())
                            }
                            TabNavItem(HistoryTabScreen) {
                                it.replaceAll(AuthScreen())
                            }
                            TabNavItem(ProfileTabScreen) {
                                it.replaceAll(AuthScreen())
                            }
                        }

                    }


                }
            )
        }
    }

    @Composable
    private fun RowScope.TabNavItem(tab: Tab, onClick: ((Navigator) -> Unit)? = null) {
        val screenModel = rememberScreenModel { TabScreenModel() }
        val tabNavigator = LocalTabNavigator.current
        val rootNavigator = RootNavigator.currentOrThrow
        val selected = tabNavigator.current == tab
        val color = if (selected) AppTheme.colors.white else AppTheme.colors.mainColor
        val background = if (selected) AppTheme.colors.mainColor else AppTheme.colors.white
        val textStyle = if (selected) AppTheme.typography.semiBold else AppTheme.typography.regular

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {
                if (onClick == null || screenModel.sessionManager.isAuth) {
                    tabNavigator.current = tab
                } else {
                    onClick.invoke(rootNavigator)
                }
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
            CustomText(
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

