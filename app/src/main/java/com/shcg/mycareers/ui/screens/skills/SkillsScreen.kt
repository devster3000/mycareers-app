package com.shcg.mycareers.ui.screens.skills

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SkillCardModel(
    val title: String,
    val description: String,
    val url: String
)

@Composable
fun SkillsScreen(
    onOpenUrl: (String) -> Unit,
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}

) {
    val skills = remember {
        listOf(
            SkillCardModel(
                title = "Being Productive",
                description = "Being productive means making the most of the time that you have, to achieve what you want to achieve.",
                url = "https://mycareers.uk/learning-modules/being-productive/#/lessons/bROh6m6ynX7R7ilW8rAGmo5qXZU67yMt"
            ),
            SkillCardModel(
                title = "Personal Brand and Identity",
                description = "This e-learning module will explore what is meant by personal brand and professional identity.",
                url = "https://mycareers.uk/learning-modules/personal-brand"
            ),
            SkillCardModel(
                title = "Study Skills",
                description = "Study skills are those skills that directly support you to learn.",
                url = "https://mycareers.uk/learning-modules/study-skills"
            ),
            SkillCardModel(
                title = "More Study Skills",
                description = "Study skills for T-level and apprentice students",
                url = "https://mycareers.uk/learning-modules/study-skills-work-placement"
            ),
            SkillCardModel(
                title = "Taking Responsibility",
                description = "This e-learning module explores what is meant by taking responsibility, the advantages of this and how to do it.",
                url = "https://mycareers.uk/learning-modules/taking-responsibility"
            ),
            SkillCardModel(
                title = "Writing your CV",
                description = "This module has been designed to help you write your CV.",
                url = "https://mycareers.uk/learning-modules/cv-writing"
            )
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Employment Skills",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(skills) { skill ->
                    SkillCard(
                        model = skill,
                        onClick = { onOpenUrl(skill.url) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SkillCard(
    model: SkillCardModel,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(198.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            Text(
                text = model.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = model.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.weight(1f))

            ContinueButton(onClick = onClick)
        }
    }
}

@Composable
private fun ContinueButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .clip(RoundedCornerShape(22.dp))
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.18f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.PlayArrow,
                    contentDescription = "Continue",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(14.dp)
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Continue",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }
    }
}
