package com.shcg.mycareers.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shcg.mycareers.data.darkModeFlow
import com.shcg.mycareers.data.nameFlow
import com.shcg.mycareers.data.setDarkMode
import com.shcg.mycareers.data.setName
import kotlinx.coroutines.launch

private object SettingsUi {
    val Purple = Color(0xFF5B4BB7)
    val Bg = Color.White
    val CardBg = Color(0xFFF2EEF8)
    val Text = Color(0xFF111111)
    val Muted = Color(0xFF6A6A6A)
    val Divider = Color(0xFFDDD6EA)
}

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onOpenPrivacyPolicy: () -> Unit,
    onOpenTerms: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Read global settings (DataStore) directly inside this screen
    val darkModeEnabled by darkModeFlow(context).collectAsState(initial = false)
    val name by nameFlow(context).collectAsState(initial = "John Doe")

    // Name edit dialog state (local to screen)
    var showEdit by remember { mutableStateOf(false) }
    var nameDraft by remember { mutableStateOf("") }

    if (showEdit) {
        AlertDialog(
            onDismissRequest = { showEdit = false },
            title = { Text("Name", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = nameDraft,
                    onValueChange = { nameDraft = it },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch { setName(context, nameDraft.trim().ifBlank { "John Doe" }) }
                    showEdit = false
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showEdit = false }) { Text("Cancel") }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SettingsUi.Bg)
            .padding(horizontal = 18.dp)
    ) {
        Spacer(Modifier.height(18.dp))

        // Back button
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(SettingsUi.Purple)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(Modifier.height(22.dp))

        Text(
            text = "Settings",
            fontSize = 40.sp,
            fontWeight = FontWeight.Black,
            color = SettingsUi.Text,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(26.dp))

        SectionHeader(icon = Icons.Outlined.PersonOutline, title = "Profile & Customisation")
        Spacer(Modifier.height(10.dp))

        // Profile card
        Surface(
            color = SettingsUi.CardBg,
            shape = RoundedCornerShape(18.dp),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {
            Column(Modifier.padding(18.dp)) {

                // Name row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Name", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SettingsUi.Text)
                    Spacer(Modifier.width(18.dp))
                    Text(name, fontSize = 13.sp, color = SettingsUi.Muted, modifier = Modifier.weight(1f))

                    IconButton(onClick = {
                        nameDraft = name
                        showEdit = true
                    }) {
                        Icon(Icons.Outlined.Edit, contentDescription = "Edit name", tint = SettingsUi.Text)
                    }
                }

                Divider(color = SettingsUi.Divider, thickness = 1.dp, modifier = Modifier.padding(top = 6.dp))
                Spacer(Modifier.height(14.dp))

                // Dark mode row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dark Mode",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = SettingsUi.Text
                    )
                    Spacer(Modifier.weight(1f))

                    Switch(
                        checked = darkModeEnabled,
                        onCheckedChange = { enabled ->
                            scope.launch { setDarkMode(context, enabled) }
                        },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = Color(0xFFBDB7C9),
                            uncheckedTrackColor = Color(0xFFBDB7C9),
                            checkedThumbColor = Color(0xFFEFEFEF),
                            uncheckedThumbColor = Color(0xFFEFEFEF),
                            checkedBorderColor = Color(0xFF8E879E),
                            uncheckedBorderColor = Color(0xFF8E879E)
                        )
                    )
                }
            }
        }

        Spacer(Modifier.height(22.dp))

        SectionHeader(icon = Icons.Outlined.Info, title = "About")
        Spacer(Modifier.height(10.dp))

        // About card
        Surface(
            color = SettingsUi.CardBg,
            shape = RoundedCornerShape(18.dp),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
        ) {
            Column(Modifier.padding(18.dp)) {
                AboutRow(title = "Privacy Policy", onClick = onOpenPrivacyPolicy)
                Divider(color = SettingsUi.Divider, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
                AboutRow(title = "Terms of Service", onClick = onOpenTerms)
            }
        }
    }
}

@Composable
private fun SectionHeader(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = SettingsUi.Text, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(10.dp))
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SettingsUi.Text)
    }
}

@Composable
private fun AboutRow(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Black, color = SettingsUi.Text)
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(SettingsUi.Purple)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.Link, contentDescription = "Open $title", tint = Color.White, modifier = Modifier.size(20.dp))
        }
    }
}
