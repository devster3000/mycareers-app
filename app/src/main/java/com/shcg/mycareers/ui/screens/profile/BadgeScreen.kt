import android.os.Build
import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.shcg.mycareers.data.*
import androidx.compose.ui.window.Dialog
import com.shcg.mycareers.R


@Composable
fun BadgesScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val earnedIds by BadgeStore.earnedBadgeIds(context).collectAsState(initial = emptySet())

    var selectedBadge by remember { mutableStateOf<Module?>(null) }
    var selectedBadgeEarned by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
        ) {
            Spacer(Modifier.height(14.dp))

            // Top row: back button and title
            Box(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { onBack() }
                        .align(Alignment.CenterStart),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Text(
                    text = "Your Badges",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }

            Spacer(Modifier.height(14.dp))

            // Big rounded container
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(22.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 18.dp)
                ) {
                    items(courseItems, key = { it.id }) { course ->
                        val modules = modulesByCourseId[course.id].orEmpty()
                        val courseIcon: Any = courseIconFor(course.id)

                        CourseBadgeSection(
                            title = course.name,
                            icon = courseIcon as ImageVector,
                            modules = modules,
                            earnedIds = earnedIds,
                            onBadgeClick = { module, earned ->
                                selectedBadge = module
                                selectedBadgeEarned = earned
                            }
                        )
                    }
                }
            }
        }

        if (selectedBadge != null) {
            BadgePopup(
                module = selectedBadge!!,
                earned = selectedBadgeEarned,
                onDismiss = { selectedBadge = null }
            )
        }
    }
}

@Composable
private fun CourseBadgeSection(
    title: String,
    icon: ImageVector,
    modules: List<Module>,
    earnedIds: Set<Int>,
    onBadgeClick: (Module, Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            modules.take(4).forEach { module ->
                val earned = earnedIds.contains(module.id)
                BadgeCircle(
                    module = module,
                    earned = earned,
                    onClick = { onBadgeClick(module, earned) }
                )
            }
        }

        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
    }
}

@Composable
private fun BadgeCircle(
    module: Module,
    earned: Boolean,
    onClick: () -> Unit
) {
    val circleSize = 56.dp

    Surface(
        modifier = Modifier
            .size(circleSize)
            .clip(CircleShape)
            .clickable { onClick() },
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(module.imageRes),
                contentDescription = module.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .graphicsLayer {
                        if (!earned) {
                            renderEffect = RenderEffect
                                .createBlurEffect(10f, 10f, Shader.TileMode.CLAMP)
                                .asComposeRenderEffect()
                        }
                        alpha = if (earned) 1f else 0.28f
                    }
            )
        }
    }
}


@Composable
private fun BadgePopup(
    module: Module,
    earned: Boolean,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_award_star_24) ,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "Badge",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = module.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        color = Color.Transparent,
                        modifier = Modifier.size(120.dp)
                    ) {
                        Image(
                            painter = painterResource(module.imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .graphicsLayer {
                                    if (!earned) {
                                        renderEffect = RenderEffect
                                            .createBlurEffect(10f, 10f, Shader.TileMode.CLAMP)
                                            .asComposeRenderEffect()
                                    }
                                    alpha = if (earned) 1f else 0.28f
                                }
                        )
                    }
                }

                Text(
                    text = if (earned)
                        "This badge is earned by completing this module."
                    else
                        "This badge is locked. Complete the module to earn it.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    }
}


@Composable
private fun courseIconFor(courseId: Any) = when (courseId) {
    1 -> Icons.Outlined.LocalShipping
    2 -> Icons.Outlined.VolunteerActivism
    3 -> Icons.Outlined.Palette
    4 -> Icons.Outlined.SoupKitchen
    5 -> Icons.Outlined.Construction
    6 -> Icons.Outlined.Computer
    else -> Icons.Outlined.StarBorder
}

