@file:Suppress("DEPRECATION")

package com.shcg.mycareers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shcg.mycareers.data.courseItems
import com.shcg.mycareers.ui.components.BottomAppNav
import com.shcg.mycareers.ui.components.TopAppBar
import com.shcg.mycareers.ui.screens.course.CourseScreen
import com.shcg.mycareers.ui.screens.course.ModuleScreen
import com.shcg.mycareers.ui.screens.course.ModuleWebViewScreen
import com.shcg.mycareers.ui.screens.home.HomeScreen
import com.shcg.mycareers.ui.screens.profile.ProfileScreen
import com.shcg.mycareers.ui.screens.settings.SettingsScreen
import com.shcg.mycareers.ui.screens.skills.SkillsScreen


object Routes {
    const val Home = "home"
    const val Courses = "courses"
    const val Skills = "skills"
    const val Profile = "profile"
    const val Settings = "settings"

    // Modules
    const val Modules = "modules/{courseId}"
    const val WebView = "webview/{url}"
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyCareers() }
        }
    }

@Composable
fun MyCareers() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false  // true = black icons, false = white icons

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(0xFFFFFFFF),
            darkIcons = useDarkIcons
        )
    }
    val nav = rememberNavController()

    MaterialTheme {
        Scaffold(
            // Top Bar - Profile and Set icons navigation
            topBar = {
                TopAppBar(
                    title = "",
                    onProfileClick = { nav.navigate(Routes.Profile) },
                    onSettingsClick = { nav.navigate(Routes.Settings) }
                )
            },

            // Bottom bar - Navigation bar - Home, Courses, Skills and Profile.
            bottomBar = {
                BottomAppNav(
                    currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route,
                    onNavigate = { route ->
                        if (route != nav.currentDestination?.route) {
                            nav.navigate(route) {
                                popUpTo(Routes.Home) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }

                )
            }
        ) { innerPadding ->
            NavHost(navController = nav, startDestination = Routes.Home, modifier = Modifier.padding(innerPadding)) {
                composable(Routes.Home) {
                    HomeScreen()
                }

                composable(Routes.Courses) {
                    CourseScreen { course ->
                        nav.navigate("modules/${course.id}")
                    }
                }

                composable(
                    "modules/{courseId}",
                    arguments = listOf(navArgument("courseId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val courseId = backStackEntry.arguments?.getInt("courseId") ?: 0
                    val course = courseItems.first { it.id == courseId }
                    ModuleScreen(course = course) { module ->
                        module.url?.let { nav.navigate("webview/${it}") }
                    }
                }

                composable(
                    "webview/{url}",
                    arguments = listOf(navArgument("url") { type = NavType.StringType })
                ) { backStackEntry ->
                    val url = backStackEntry.arguments?.getString("url") ?: ""
                    ModuleWebViewScreen(url)
                }


                composable(Routes.Skills) {
                    SkillsScreen()
                }
                composable(Routes.Profile) {
                    ProfileScreen()
                }
                composable(Routes.Settings) {
                    SettingsScreen()
                }
            }
        }
    }
}
