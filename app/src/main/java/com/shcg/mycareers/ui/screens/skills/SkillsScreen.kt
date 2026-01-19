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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SkillCardModel(
    val title: String,
    val description: String,
    val url: String
)

private object SkillUi {
    val Purple = Color(0xFF5B4BB7)
    val PurpleSoft = Color(0xFFE9E4FF)
    val CardBg = Color(0xFFF7F5FF)
    val CardBorder = Color(0xFFD9D3F3)
    val TextPrimary = Color(0xFF111111)
    val TextSecondary = Color(0xFF5A5A5A)
}

@Composable
fun SkillsScreen(
    onOpenUrl: (String) -> Unit
) {
    val skills = remember {
        listOf(
            SkillCardModel(
                title = "Being Productive",
                description = "Being productive means making the most of the time that you have, to achieve what you want to achieve.",
                url = "https://mycareers.uk/learning-modules/being-productive"
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
            ),
        )
    }

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
                    .background(SkillUi.PurpleSoft),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Profile",
                    tint = SkillUi.Purple,
                    modifier = Modifier.size(22.dp)
                )
            }

            IconButton(onClick = { /* your settings action */ }) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings",
                    tint = SkillUi.TextPrimary
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Employment Skills",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = SkillUi.TextPrimary
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

@Composable
private fun SkillCard(
    model: SkillCardModel,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(14.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(198.dp)
            .clickable { onClick() },
        shape = shape,
        color = SkillUi.CardBg,
        border = BorderStroke(1.dp, SkillUi.CardBorder),
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
                color = SkillUi.TextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = model.description,
                fontSize = 12.sp,
                color = SkillUi.TextSecondary,
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
    val shape = RoundedCornerShape(22.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .clip(shape)
            .clickable { onClick() },
        color = SkillUi.Purple,
        shape = shape
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
                    .background(Color.White.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.PlayArrow,
                    contentDescription = "Continue",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Continue",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }
    }
}
