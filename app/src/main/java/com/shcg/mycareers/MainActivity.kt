// MainActivity.kt (UPDATED to work with your CourseScreen/ModuleScreen/WebViewScreen)
//
// Key fixes:
// - Your Course IDs are Strings, not Ints -> route arg is StringType
// - Your WebView route must be query-based (or you must encode slashes). Here we encode and use webview?url=...
// - Hook up CourseScreen(onOpenCourse) -> ModuleScreen(courseId)
// - Hook up ModuleScreen(onOpenModuleUrl) -> WebViewScreen(url)

@file:Suppress("DEPRECATION")

package com.shcg.mycareers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

// IMPORTANT: these imports must match where you put the file you pasted.
// If your CoursesScreen.kt is in com.example.app.courses, change the package to com.shcg.mycareers... or update imports.
import com.example.app.courses.CourseScreen
import com.example.app.courses.ModuleScreen
import com.example.app.courses.WebViewScreen

object Routes {
    const val Home = "home"
    const val Courses = "courses"
    const val Skills = "skills"
    const val Profile = "profile"
    const val Settings = "settings"

    // Course -> Modules -> WebView
    const val Modules = "modules/{courseId}"
    const val WebView = "webview?url={url}"

    fun modules(courseId: String) = "modules/$courseId"
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
    val useDarkIcons = false

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(0xFFFFFFFF),
            darkIcons = useDarkIcons
        )
    }

    val nav = rememberNavController()

    MaterialTheme {
        Scaffold(
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
                composable(Routes.Home) { HomeScreen() }

                composable(Routes.Courses) {
                    CourseScreen(
                        onOpenCourse = { courseId ->
                            nav.navigate(Routes.modules(courseId))
                        }
                    )
                }

                composable(
                    route = Routes.Modules,
                    arguments = listOf(navArgument("courseId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val courseId = backStackEntry.arguments?.getString("courseId").orEmpty()

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
                    }
                ) }

                composable(Routes.Profile) { ProfileScreen() }
                composable(Routes.Settings) { SettingsScreen() }
            }
        }
    }
}
