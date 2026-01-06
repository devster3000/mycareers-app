package com.shcg.mycareers.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun TopAppBar(title: Any, onProfileClick: () -> Unit, onSettingsClick: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

     CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "Profile"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
            )
        }