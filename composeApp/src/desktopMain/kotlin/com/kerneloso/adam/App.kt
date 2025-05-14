package com.kerneloso.adam

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import com.kerneloso.adam.ui.screens.HomeScreen
import com.kerneloso.adam.ui.theme.Theme

@Composable
fun App() {
    Theme {
        Navigator(HomeScreen())
    }
}