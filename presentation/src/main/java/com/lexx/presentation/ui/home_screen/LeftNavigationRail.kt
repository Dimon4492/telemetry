package com.lexx.presentation.ui.home_screen

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.lexx.presentation.models.navigation.NavigationItemContent
import com.lexx.presentation.ui.navigation.AppContentType

@Composable
fun LeftNavigationRail(
    currentTab: AppContentType,
    onTabPressed: ((AppContentType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
) {
    NavigationRail {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.appContentType,
                onClick = { onTabPressed(navItem.appContentType) },
                label = {
                    Text(
                        text = navItem.tabLabel
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.tabIcon),
                        null
                    )
                }
            )
        }
    }
}
