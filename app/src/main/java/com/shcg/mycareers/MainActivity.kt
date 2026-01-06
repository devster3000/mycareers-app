package com.shcg.mycareers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shcg.mycareers.ui.screens.course.CourseScreen
import com.shcg.mycareers.ui.screens.home.HomeScreen
import com.shcg.mycareers.ui.screens.profile.ProfileScreen
import com.shcg.mycareers.ui.screens.settings.SettingsScreen
import com.shcg.mycareers.ui.screens.skills.SkillsScreen
import com.shcg.mycareers.ui.theme.MyCareersTheme


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
        NavHost(navController = nav, startDestination = Routes.Home) {

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

            composable (Routes.Settings) {
                SettingsScreen()
            }
        }
    }
}
