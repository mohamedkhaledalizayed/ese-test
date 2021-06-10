package com.neqabty.presentation.entities

data class NavigationMenuItem(
    var iconResId: Int,
    var nameResId: Int,
    var callback: () -> Unit = {}
)