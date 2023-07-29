package com.lexx.presentation.models.navigation

import com.lexx.presentation.ui.navigation.AppContentType

data class NavigationItemContent (
    val appContentType: AppContentType,
    val tabIcon: Int,
    val tabLabel: String,
    val pageTitle: String,
)
