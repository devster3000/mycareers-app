package com.shcg.mycareers.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import com.shcg.mycareers.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shcg.mycareers.data.darkModeFlow
import com.shcg.mycareers.data.nameFlow
import com.shcg.mycareers.data.setDarkMode
import com.shcg.mycareers.data.setName
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onOpenPrivacyPolicy: () -> Unit,
    onOpenTerms: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val darkModeEnabled by darkModeFlow(context).collectAsState(initial = false)
    val name by nameFlow(context).collectAsState(initial = "Gordon Freeman")

    var showEdit by remember { mutableStateOf(false) }
    var nameDraft by remember { mutableStateOf("") }

    if (showEdit) {
        AlertDialog(
            onDismissRequest = { showEdit = false },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurface,
            title = { Text("Name", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = nameDraft,
                    onValueChange = { nameDraft = it },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch { setName(context, nameDraft.trim().ifBlank { "Gordon Freeman" }) }
                    showEdit = false
                }) {
                    Text("Save", color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEdit = false }) {
                    Text("Cancel", color = MaterialTheme.colorScheme.primary)
                }
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
        ) {
            Spacer(Modifier.height(18.dp))

            // Back button
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.height(22.dp))

            Text(
                text = "Profile",
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(26.dp))

            SectionHeader(icon = Icons.Outlined.PersonOutline, title = "Profile & Customisation")
            Spacer(Modifier.height(10.dp))

            // Profile card
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Column(Modifier.padding(18.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Name",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.width(18.dp))
                        Text(
                            name,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(onClick = {
                            nameDraft = name
                            showEdit = true
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit name",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Divider(
                        color = MaterialTheme.colorScheme.outline,
                        thickness = 1.dp,
                        modifier = Modifier.padding(top = 6.dp)
                    )

                    Spacer(Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        Text(
//                            text = "Dark Mode",
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Black,
//                            color = MaterialTheme.colorScheme.onSurface
//                        )
//                        Spacer(Modifier.weight(1f))

//                        Switch(
//                            checked = darkModeEnabled,
//                            onCheckedChange = { enabled ->
//                                scope.launch { setDarkMode(context, enabled) }
//                            },
//                            colors = SwitchDefaults.colors(
//                                checkedTrackColor = MaterialTheme.colorScheme.primary,
//                                uncheckedTrackColor = MaterialTheme.colorScheme.outline,
//                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
//                                uncheckedThumbColor = MaterialTheme.colorScheme.surface,
//                                checkedBorderColor = MaterialTheme.colorScheme.primary,
//                                uncheckedBorderColor = MaterialTheme.colorScheme.outline
//                            )
//                        )
                    }
                }
            }

            Spacer(Modifier.height(22.dp))

            SectionHeader2(
                icon = painterResource(id = R.drawable.outline_award_star_24),
                title = "Badges"
            )
            Spacer(Modifier.height(10.dp))

            // Badge card
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {

            }

            SectionHeader(icon = Icons.Outlined.Info, title = "About")
            Spacer(Modifier.height(22.dp))

            // About card
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    AboutRow(title = "Privacy Policy", onClick = onOpenPrivacyPolicy)
                    Divider(
                        color = MaterialTheme.colorScheme.outline,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    AboutRow(title = "Terms of Service", onClick = onOpenTerms)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun SectionHeader2(
    icon: Painter,
    title: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
fun AboutRow(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Link,
                contentDescription = "Open $title",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun BadgeRow(
    icon: Painter,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

    }
}
