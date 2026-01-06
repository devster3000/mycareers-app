package com.shcg.mycareers.ui.components

// === Icons ===
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
//import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.School
//import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.Star
//import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
//import androidx.compose.material.icons.filled.AccountCircle

// === Components ===

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.vector.ImageVector
import com.shcg.mycareers.Routes


//data class Tab(
//    val route: String,
//    val label: String,
//    val filledIcon: ImageVector,
//    val outlinedIcon: ImageVector
//)
//
//val Tabs = listOf(
//    Tab(route = "home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
//    Tab(route = "courses", "Courses", Icons.Filled.School, Icons.Outlined.School),
//    Tab(route = "skills", "Skills", Icons.Filled.Star, Icons.Outlined.Star),
//    Tab(route = "profile", "Profile", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle)
//)

@Composable
fun BottomAppNav(currentRoute: String?, onNavigate: (String) -> Unit) {
            NavigationBar {

                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(Routes.Home) },
                    icon = { Icon(Icons.Outlined.Home, null) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(Routes.Courses) },
                    icon = { Icon(Icons.Outlined.School, null) },
                    label = { Text("Courses") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(Routes.Skills) },
                    icon = { Icon(Icons.Outlined.Star, null) },
                    label = { Text("Skills") },
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(Routes.Profile) },
                    icon = { Icon(Icons.Outlined.AccountCircle, null) },
                    label = { Text("You",) }
                )
            }
        }