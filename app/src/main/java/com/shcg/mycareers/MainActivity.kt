@file:Suppress("DEPRECATION")

package com.shcg.mycareers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shcg.mycareers.ui.components.BottomAppNav
import com.shcg.mycareers.ui.components.TopAppBar
import com.shcg.mycareers.ui.screens.home.HomeScreen
import com.shcg.mycareers.ui.screens.profile.ProfileScreen
import com.shcg.mycareers.ui.screens.settings.SettingsScreen
import com.shcg.mycareers.ui.screens.skills.SkillsScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.shcg.mycareers.data.darkModeFlow
import com.shcg.mycareers.data.dynamicColorFlow
import com.shcg.mycareers.ui.screens.course.CourseScreen
import com.shcg.mycareers.ui.screens.course.ModuleScreen
import com.shcg.mycareers.ui.screens.course.WebViewScreen
import com.shcg.mycareers.ui.theme.MyCareersTheme
import androidx.compose.foundation.isSystemInDarkTheme


object Routes {
    const val Home = "home"
    const val Courses = "courses"
    const val Skills = "skills"
    const val Profile = "profile"
    const val Settings = "settings"
    const val Modules = "modules/{courseId}"
    const val WebView = "webview?url={url}"
    fun modules(courseId: Int) = "modules/$courseId"
    fun webview(encodedUrl: String) = "webview?url=$encodedUrl"
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
    // Dark Status Icons
    val isDarkTheme = isSystemInDarkTheme()
    val useDarkIcons = !isDarkTheme
    val context = LocalContext.current
    val darkMode = darkModeFlow(context).collectAsState(initial = false).value
    val dynamicColorEnabled =
        dynamicColorFlow(LocalContext.current)
            .collectAsState(initial = true)
            .value

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }

    val nav = rememberNavController()

    MyCareersTheme(
        darkMode = darkMode,
        dynamicColor = dynamicColorEnabled) {


        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
//            topBar = {
//                TopAppBar(
//                    title = "",
//                    onProfileClick = { nav.navigate(Routes.Profile) },
//                    onSettingsClick = { nav.navigate(Routes.Settings) }
//                )
//            },
            bottomBar = {
                BottomAppNav(
                    currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route,
                    onNavigate = { route ->
                        if (route != nav.currentDestination?.route) {
                            nav.navigate(route) {
                                popUpTo(Routes.Home) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = nav,
                startDestination = Routes.Home,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Routes.Home) { HomeScreen(
                    onOpenCourse = { courseId -> nav.navigate(Routes.modules(courseId)) },
                    onProfileClick = { nav.navigate(Routes.Profile) },
                    onSettingsClick = { nav.navigate(Routes.Settings) }) }

                composable(Routes.Courses) {
                    CourseScreen(
                        onOpenCourse = { courseId ->
                            nav.navigate(Routes.modules(courseId))

                        },
                        onProfileClick = { nav.navigate(Routes.Profile) },
                        onSettingsClick = { nav.navigate(Routes.Settings) }

                    )
                }

                composable(
                    route = Routes.Modules,
                    arguments = listOf(navArgument("courseId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val courseId = backStackEntry.arguments?.getInt("courseId") ?: 0

                    ModuleScreen(
                        courseId = courseId,
                        onOpenModuleUrl = { url ->
                            val encoded = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                            nav.navigate(Routes.webview(encoded))
                        }
                    )
                }

                composable(
                    route = Routes.WebView,
                    arguments = listOf(navArgument("url") { type = NavType.StringType })
                ) { backStackEntry ->
                    val encoded = backStackEntry.arguments?.getString("url").orEmpty()
                    val decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())
                    WebViewScreen(url = decoded)
                }

                composable(Routes.Skills) { SkillsScreen(
                    onOpenUrl = { url ->
                        val encoded = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                        nav.navigate(Routes.webview(encoded))
                    },

                    onProfileClick = { nav.navigate(Routes.Profile) },
                    onSettingsClick = { nav.navigate(Routes.Settings) }
                ) }

                composable(Routes.Profile) { ProfileScreen(onBack = { nav.popBackStack() },
                    onOpenPrivacyPolicy = {
                        val encoded = URLEncoder.encode("https://mycareers.uk/privacy-policy/", "UTF-8")
                        nav.navigate(Routes.webview(encoded))
                    },
                    onOpenTerms = {
                        val encoded = URLEncoder.encode("https://mycareers.uk/privacy-policy/", "UTF-8")
                        nav.navigate(Routes.webview(encoded))
                    }) }

                composable(Routes.Settings) { SettingsScreen(onBack = { nav.popBackStack() },
                    onOpenPrivacyPolicy = {
                        val encoded = URLEncoder.encode("https://mycareers.uk/privacy-policy/", "UTF-8")
                        nav.navigate(Routes.webview(encoded))
                    },
                    onOpenTerms = {
                        val encoded = URLEncoder.encode("https://mycareers.uk/privacy-policy/", "UTF-8")
                        nav.navigate(Routes.webview(encoded))
                    }) }
            }
        }
    }
}
