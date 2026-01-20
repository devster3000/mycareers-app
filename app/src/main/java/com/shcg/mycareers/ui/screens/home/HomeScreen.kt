package com.shcg.mycareers.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shcg.mycareers.data.favouritesFlow
import com.shcg.mycareers.data.toggleFavourite
import com.shcg.mycareers.ui.screens.course.Course
import com.shcg.mycareers.ui.screens.course.ModuleState
import com.shcg.mycareers.ui.screens.course.buildCourses
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    courses: List<Course> = remember { buildCourses() },
    onOpenCourse: (courseId: String) -> Unit,
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val favourites by favouritesFlow(context).collectAsState(initial = emptySet())
    var showFavSheet by remember { mutableStateOf(false) }

    val hasAnyProgress = remember(courses) {
        courses.any { c ->
            c.modules.any { it.state == ModuleState.CONTINUE || it.state == ModuleState.COMPLETED }
        }
    }

    val continueCourses = remember(courses) {
        courses.filter { c ->
            c.modules.any { it.state == ModuleState.CONTINUE || it.state == ModuleState.COMPLETED }
        }
    }

    val recommendedCourses = remember(courses) {
        val wanted = setOf("maritime", "digital")
        courses.filter { it.id in wanted }
    }

    val favouriteCourses = remember(courses, favourites) {
        courses.filter { it.id in favourites }
    }

    if (showFavSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFavSheet = false },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Text(
                text = "Add to Favourites",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .padding(bottom = 18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                courses.forEach { course ->
                    val isFav = favourites.contains(course.id)

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isFav) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .clickable { scope.launch { toggleFavourite(context, course.id) } }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = course.title,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = if (isFav) "Added" else "Add",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    // 🔑 Theme-driven base layer
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
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable { onProfileClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                IconButton(onClick = onSettingsClick) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Home",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(18.dp))

            if (hasAnyProgress) {
                HomeSection(
                    title = "Continue..",
                    courses = continueCourses,
                    onOpenCourse = onOpenCourse
                )
                Spacer(Modifier.height(20.dp))
            }

            HomeSection(
                title = "Recommended",
                courses = recommendedCourses,
                onOpenCourse = onOpenCourse
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Favourites",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(end = 18.dp)
            ) {
                items(favouriteCourses, key = { it.id }) { course ->
                    HomeCourseCard(course = course, onClick = { onOpenCourse(course.id) })
                }

                item(key = "add_favourite_card") {
                    AddFavouriteCard(onClick = { showFavSheet = true })
                }
            }
        }
    }
}

@Composable
private fun HomeSection(
    title: String,
    courses: List<Course>,
    onOpenCourse: (String) -> Unit
) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(Modifier.height(12.dp))

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(end = 18.dp)
    ) {
        items(courses, key = { it.id }) { course ->
            HomeCourseCard(course = course, onClick = { onOpenCourse(course.id) })
        }
    }
}

@Composable
private fun HomeCourseCard(
    course: Course,
    onClick: () -> Unit
) {
    // NOTE: course cards are intentionally "brand coloured" (course.cardBg)
    // so we keep that. Everything else uses MaterialTheme.
    Surface(
        modifier = Modifier
            .width(280.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(26.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(26.dp),
        color = course.cardBg,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Box(Modifier.fillMaxSize()) {
            if (course.heroRes != null) {
                Image(
                    painter = androidx.compose.ui.res.painterResource(course.heroRes),
                    contentDescription = course.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun AddFavouriteCard(
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(280.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(26.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(26.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add favourite",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(28.dp)
                        .alpha(0.35f)
                )
            }
        }
    }
}
