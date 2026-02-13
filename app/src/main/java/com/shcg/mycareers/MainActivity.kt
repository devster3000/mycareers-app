@file:Suppress("DEPRECATION")

package com.shcg.mycareers

import BadgesScreen
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
import com.shcg.mycareers.data.followSystemFlow
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.luminance
import com.shcg.mycareers.data.courseItems
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.remember

object Routes {
    const val Home = "home"
    const val Courses = "courses"
    const val Skills = "skills"
    const val Profile = "profile"
    const val Settings = "settings"
    const val Modules = "modules/{courseId}"
    const val Badge = "badges"
    const val WebView = "webview?url={url}&moduleId={moduleId}"
    fun modules(courseId: Int) = "modules/$courseId"
    fun webview(encodedUrl: String, moduleId: Int) = "webview?url=$encodedUrl&moduleId=$moduleId"
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyCareers() }
    }
}


fun openExternalUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}



@Composable
fun MyCareers() {
    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current

    val followSystem by followSystemFlow(context).collectAsState(initial = true)
    val manualDarkMode by darkModeFlow(context).collectAsState(initial = false)
    val systemDark = isSystemInDarkTheme()

    val darkModeEffective = if (followSystem) systemDark else manualDarkMode

    val dynamicColorEnabled by dynamicColorFlow(context).collectAsState(initial = true)


    val nav = rememberNavController()

    val backStackEntry by nav.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val isDark = isSystemInDarkTheme()

    val desiredStatusBg = remember(route, backStackEntry) {
        var bg = Color.Transparent

        if (route?.startsWith("modules/") == true) {
            val courseId = backStackEntry?.arguments?.getInt("courseId")
            val course = courseItems.firstOrNull { it.id == courseId }
            if (course != null) bg = course.secColourCourse
        } else if (route?.startsWith("webview") == true) {
            bg = Color(13, 29, 53)
        }

        bg
    }

    val darkIcons = remember(desiredStatusBg, darkModeEffective, route) {
        when {
            route?.startsWith("webview") == true -> false
            desiredStatusBg != Color.Transparent -> desiredStatusBg.luminance() > 0.5f
            else -> !darkModeEffective
        }
    }

    LaunchedEffect(route, desiredStatusBg, darkIcons) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = darkIcons
        )
    }


    MyCareersTheme(
        darkMode = darkModeEffective,
        dynamicColor = dynamicColorEnabled
    ) {

        Scaffold(
            containerColor = colorScheme.background,
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
            Box(Modifier.fillMaxSize()) {

                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(desiredStatusBg)
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                )

                NavHost(
                    navController = nav,
                    startDestination = Routes.Home,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    composable(Routes.Home) {
                        HomeScreen(
                            onOpenCourse = { courseId -> nav.navigate(Routes.modules(courseId)) },
                            onProfileClick = { nav.navigate(Routes.Profile) },
                            onSettingsClick = { nav.navigate(Routes.Settings) })
                    }

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
                            onOpenModuleUrl = { url, moduleId ->
                                val encoded =
                                    URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                                nav.navigate(Routes.webview(encoded, moduleId))
                            },
                            onProfileClick = { nav.navigate(Routes.Profile) },
                            onSettingsClick = { nav.navigate(Routes.Settings) }
                        )
                    }

                    composable(
                        route = Routes.Badge,
                    ) {
                        BadgesScreen(
                            onBack = { nav.popBackStack() }
                        )
                    }

                    composable(
                        route = Routes.WebView,
                        arguments = listOf(
                            navArgument("url") { type = NavType.StringType },
                            navArgument("moduleId") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val encoded = backStackEntry.arguments?.getString("url").orEmpty()
                        val decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())
                        val moduleId = backStackEntry.arguments?.getInt("moduleId") ?: -1

                        WebViewScreen(url = decoded, moduleId = moduleId)
                    }



                    composable(Routes.Skills) {
                        SkillsScreen(
                            onOpenUrl = { url ->
                                openExternalUrl(context, url)
                            },

                            onProfileClick = { nav.navigate(Routes.Profile) },
                            onSettingsClick = { nav.navigate(Routes.Settings) }
                        )
                    }

                    composable(Routes.Profile) {
                        ProfileScreen(
                            onBack = { nav.popBackStack() },

                            onOpenBadges = { nav.navigate(Routes.Badge) },
                            onOpenPrivacyPolicy = {
                                openExternalUrl(context, "https://mycareers.uk/privacy-policy/")
                            },
                            onOpenTerms = {
                                openExternalUrl(context, "https://mycareers.uk/privacy-policy/")
                            })
                    }

                    composable(Routes.Settings) {
                        SettingsScreen(
                            onBack = { nav.popBackStack() },
                            onOpenPrivacyPolicy = {
                                openExternalUrl(context, "https://mycareers.uk/privacy-policy/")
                            },
                            onOpenTerms = {
                                openExternalUrl(context, "https://mycareers.uk/privacy-policy/")
                            })
                    }
                }
            }
        }
    }
}
