package com.shcg.mycareers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shcg.mycareers.ui.components.TopAppBar
import com.shcg.mycareers.ui.screens.course.CourseScreen
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
    val nav = rememberNavController()

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = "",
                    onProfileClick = { nav.navigate(Routes.Profile) },
                    onSettingsClick = { nav.navigate(Routes.Settings) }
                )
            },
        ) { innerPadding ->
            NavHost(navController = nav, startDestination = Routes.Home, modifier = Modifier.padding(innerPadding)) {
                composable(Routes.Home) {
                    HomeScreen()
                }
                composable(Routes.Courses) {
                    CourseScreen()
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
