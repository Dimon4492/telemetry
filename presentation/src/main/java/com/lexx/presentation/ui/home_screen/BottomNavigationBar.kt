package com.lexx.presentation.ui.home_screen

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.lexx.presentation.models.navigation.NavigationItemContent
import com.lexx.presentation.ui.navigation.AppContentType

@Composable
fun BottomNavigationBar(
    currentTab: AppContentType,
    onTabPressed: ((AppContentType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        for (item in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == item.appContentType,
                onClick = { onTabPressed(item.appContentType) },
                label = {
                    Text(
                        text = item.tabLabel
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.tabIcon),
                        contentDescription = null
                    )
                }
            )
        }
    }
}
