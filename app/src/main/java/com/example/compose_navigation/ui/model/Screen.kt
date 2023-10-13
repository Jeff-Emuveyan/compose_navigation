package com.example.compose_navigation.ui.model

import androidx.annotation.StringRes
import com.example.compose_navigation.R

sealed class Screen(val route: String, @StringRes val title: Int) {
    data object Home : Screen(Route.HOME, R.string.home)
    data object SignIn : Screen(Route.SIGNIN, R.string.signin)
    data object SignUp : Screen(Route.SIGNUP, R.string.signup)
    data object Movies : Screen(Route.MOVIES, R.string.movies)
    data object Bookmarks : Screen(Route.BOOKMARK, R.string.bookmarks)
    data object DashBoard : Screen(Route.DASHBOARD, R.string.dashboard)
}