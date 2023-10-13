package com.example.compose_navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.bookmarks.BookmarksScreen
import com.example.compose_navigation.ui.model.Route
import com.example.compose_navigation.ui.model.Screen
import com.example.compose_navigation.ui.theme.Compose_navigationTheme
import com.example.dashboard.DashBoardScreen
import com.example.home.HomeScreen
import com.example.home.SignInScreen
import com.example.home.SignUpScreen
import com.example.movies.MoviesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val items = listOf(
        Screen.Home,
        Screen.Movies,
        Screen.Bookmarks
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_navigationTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppScreen(navController)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppScreen(navController: NavHostController) {
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                            label = { Text(stringResource(screen.title)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(navController, startDestination = Route.HOME_NESTED_GRAPH, Modifier.padding(innerPadding)) {
                homeScreenNestedGraph(navController)
                composable(Screen.Movies.route) { MoviesScreen() }
                composable(Screen.Bookmarks.route) { BookmarksScreen() }
                composable("${Screen.DashBoard.route}/{userName}") {
                    // We can easily get the userName from the route by doing this:
                    // val userName = it.arguments?.getString("userName") ?: ""
                    // and then pass the userName as a param to the DashBoardScreen composable method:
                    // DashBoardScreen(userName),
                    // as explained here: https://developer.android.com/jetpack/compose/navigation#nav-with-args

                    // Another alternative method of getting the userName from route is through the ViewModel.
                    // This method is used if we need to send data gotten from the route to the repository.
                    // The ViewModel can act as a middle man. It will get the data from the route and send it
                    // to the repository, as explained here: https://developer.android.com/jetpack/compose/navigation#retrieving-complex-data

                    // For our use case, we ought to use the first method, but we will use the ViewModel
                    // method just for DEMONSTRATION purposes:
                    DashBoardScreen()
                }
            }
        }
    }

    private fun NavGraphBuilder.homeScreenNestedGraph(navController: NavHostController) {

        navigation(startDestination = Screen.Home.route, route =  Route.HOME_NESTED_GRAPH) {
            composable(Screen.Home.route) { HomeScreen (
                navigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                navigateToSignIn = { navController.navigate(Screen.SignIn.route) }
            ) }
            composable(Screen.SignUp.route) { SignUpScreen{
                navController.navigate("${Screen.DashBoard.route}/$it")
            } }
            composable(Screen.SignIn.route) { SignInScreen{
                navController.navigate("${Screen.DashBoard.route}/$it")
            } }
        }
    }
}