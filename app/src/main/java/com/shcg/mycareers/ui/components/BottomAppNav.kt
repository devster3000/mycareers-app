package com.shcg.mycareers.ui.components

// === Icons ===

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.filled.AccountCircle

// === Components ===

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.navigation
import androidx.compose.runtime.*
import com.shcg.mycareers.ui.screens.course.CourseScreen
import com.shcg.mycareers.ui.screens.home.HomeScreen
import com.shcg.mycareers.ui.screens.profile.ProfileScreen
import com.shcg.mycareers.ui.screens.skills.SkillsScreen


data class Tab(
    val route: String,
    val label: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
)

val Tabs = listOf(
    Tab(route = "home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
    Tab(route = "courses", "Courses", Icons.Filled.School, Icons.Outlined.School),
    Tab(route = "skills", "Skills", Icons.Filled.Star, Icons.Outlined.Star),
    Tab(route = "profile", "Profile", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle)
)


@Composable
fun BottomNav() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route


    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("home") },
                    icon = { Icon(Icons.Outlined.Home, null) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("courses") },
                    icon = { Icon(Icons.Outlined.School, null) },
                    label = { Text("Courses") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("skills") },
                    icon = { Icon(Icons.Outlined.Star, null) },
                    label = { Text("Skills") },
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("profile") },
                    icon = { Icon(Icons.Outlined.AccountCircle, null) },
                    label = { Text("You",) }
                )
            }
        }
    ) {
        padding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") { HomeScreen() }
            composable("courses") { CourseScreen() }
            composable("skills") { SkillsScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}