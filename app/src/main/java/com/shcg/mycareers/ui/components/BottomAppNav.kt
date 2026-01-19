package com.shcg.mycareers.ui.components

// === Icons ===
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
import com.shcg.mycareers.Routes

@Composable
fun BottomAppNav(currentRoute: String?, onNavigate: (String) -> Unit) {
            NavigationBar {

                NavigationBarItem(
                    selected = currentRoute == Routes.Home,
                    onClick = { onNavigate(Routes.Home) },
                    icon = { Icon(if (currentRoute == Routes.Home) Icons.Filled.Home else Icons.Outlined.Home, null) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.Courses,
                    onClick = { onNavigate(Routes.Courses) },
                    icon = { Icon(if (currentRoute == Routes.Courses) Icons.Filled.School else Icons.Outlined.School, null) },
                    label = { Text("Courses") }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.Skills,
                    onClick = { onNavigate(Routes.Skills) },
                    icon = { Icon(if (currentRoute == Routes.Skills) Icons.Filled.Star else Icons.Outlined.Star, null) },
                    label = { Text("Skills") },
                )

//                NavigationBarItem(
//                    selected = currentRoute == Routes.Profile,
//                    onClick = { onNavigate(Routes.Profile) },
//                    icon = { Icon(if (currentRoute == Routes.Profile) Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle, null) },
//                    label = { Text("You",) }
//                )
            }
        }