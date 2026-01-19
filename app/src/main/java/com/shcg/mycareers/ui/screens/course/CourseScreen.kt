package com.shcg.mycareers.ui.screens.course

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.shcg.mycareers.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items



data class Course(
    val id: String,
    val title: String,
    val cardBg: Color,
    val border: Color,
    val heroRes: Int?,
    val modules: List<CourseModule>
)

data class CourseModule(
    val index: Int,
    val title: String,
    val subtitle: String,
    val state: ModuleState,
    val url: String
)

enum class ModuleState { COMPLETED, CONTINUE, START }


private object CoursesUi {
    val Purple = Color(0xFF5B4BB7)
    val PurpleSoft = Color(0xFFE9E4FF)
    val TextPrimary = Color(0xFF111111)
    val TextSecondary = Color(0xFF6A6A6A)
    val PageBg = Color(0xFFFFFFFF)

    val SearchBg = Color(0xFFEDEAF6)
    val CardCorner = RoundedCornerShape(22.dp)

    val ModuleCardBg = Color(0xFFF6F3FF)
    val ModuleBorder = Color(0xFFD9D3F3)
    val ModuleGreen = Color(0xFFD6F5DA)
    val ModuleGreenIcon = Color(0xFF1E7A2E)
    val ModulePurplePanel = Color(0xFFE7E0FF)
}


fun buildCourses(): List<Course> {
    return listOf(
        Course(
            id = "maritime",
            title = "Maritime & Logistics",
            cardBg = Color(0xFF27B1F6),
            border = Color(0xFF1E6DFF),
            heroRes = R.drawable.maritime,
            modules = listOf(
                CourseModule(1, "Introduction to Logistics", "Start now!", ModuleState.START, "https://mycareers.uk/maritime-and-logistics-courses/an-introduction-to-logistics/"),
                CourseModule(2, "The Supply Chain", "Start now!", ModuleState.START, "https://mycareers.uk/maritime-and-logistics-courses/the-supply-chain/"),
                CourseModule(3, "Import-Export", "Start now!", ModuleState.START, "https://mycareers.uk/maritime-and-logistics-courses/import-export/"),
                CourseModule(4, "Green Logistics", "Start now!", ModuleState.START, "https://mycareers.uk/maritime-and-logistics-courses/green-logistics/"),
            )
        ),
        Course(
            id = "health_social_care",
            title = "Health & Social Care",
            cardBg = Color(0xFF2AD07B),
            border = Color(0xFF1E6DFF),
            heroRes = R.drawable.health,
            modules = listOf(
                CourseModule(1, "Nursing", "Start now!", ModuleState.START, "https://mycareers.uk/health-and-social-courses/an-introduction-to-nursing/"),
                CourseModule(2, "Residential Social Care", "Start now!", ModuleState.START, "https://mycareers.uk/health-and-social-courses/an-introduction-to-residential-social-care/"),
                CourseModule(3, "Community Social Care", "Start now!", ModuleState.START, "https://mycareers.uk/health-and-social-courses/an-introduction-to-community-social-care/"),
                CourseModule(4, "Allied Health", "Start now!", ModuleState.START, "https://mycareers.uk/health-and-social-courses/an-introduction-to-allied-health/"),
            )
        ),

        Course(
            id = "creative",
            title = "Creative Arts",
            cardBg = Color(0xFFFFC857),
            border = Color(0xFF1E6DFF),
            heroRes = R.drawable.creative,
            modules = listOf(
                CourseModule(1, "Stage Management", "Start now!", ModuleState.START, "https://mycareers.uk/creative-arts-courses/stage-management/"),
                CourseModule(2, "Lighting", "Start now!", ModuleState.START, "https://mycareers.uk/creative-arts-courses/lighting-design/"),
                CourseModule(3, "Sound", "Start now!", ModuleState.START, "https://mycareers.uk/creative-arts-courses/sound-design/"),
                CourseModule(4, "Set & Properties Design", "Start now!", ModuleState.START, "https://mycareers.uk/creative-arts-courses/set-properties-design/"),
            )
        ),
        Course(
            id = "hospitality",
            title = "Hospitality",
            cardBg = Color(0xFFFF7AA2),
            border = Color(0xFF1E6DFF),
            heroRes = R.drawable.hospitality,
            modules = listOf(
                CourseModule(1, "Introduction to Hospitality", "Start now!", ModuleState.START, "https://mycareers.uk/hospitality-courses/an-introduction-to-the-hospitality-industry/"),
            )
        ),
        Course(
            id = "construction",
            title = "Construction",
            cardBg = Color(0xFF9B8CFF),
            border = Color(0xFF1E6DFF),
            heroRes = R.drawable.construction,
            modules = listOf(
                CourseModule(1, "Intro to Civil Engineering", "Start now!", ModuleState.START, "https://mycareers.uk/construction-courses/an-introduction-to-civil-engineering/"),
                CourseModule(2, "Plastering, Brick & Carpentry", "Start now!", ModuleState.START, "https://mycareers.uk/construction-courses/plastering-brickwork-carpentry/"),
                CourseModule(3, "Plumbing & Electrical", "Start now!", ModuleState.START, "https://mycareers.uk/construction-courses/plumbing-electrical/"),
            )
        ),
        Course(
            id = "digital",
            title = "Digital Technologies",
            cardBg = Color(0xFF7CD6FF),
            border = Color(0xFF1E6DFF),
            heroRes = R.drawable.digital,
            modules = listOf(
                CourseModule(1, "Digital Foundations", "UNAVAILABLE", ModuleState.START, "https://example.com/c6/m1"),
                CourseModule(2, "Digital Technology", "UNAVAILABLE", ModuleState.START, "https://example.com/c6/m2"),
                CourseModule(3, "Digital Creative", "UNAVAILABLE", ModuleState.START, "https://example.com/c6/m3"),
                CourseModule(4, "innovation & Emerging Tech", "UNAVAILABLE", ModuleState.START, "https://example.com/c6/m4"),
            )
        ),
    )
}

@Composable
fun CourseScreen(
    courses: List<Course> = remember { buildCourses() },
    onOpenCourse: (courseId: String) -> Unit,
    onSettingsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
) {
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoursesUi.PageBg)
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
                    .background(CoursesUi.PurpleSoft)
                    .clickable { onProfileClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Profile",
                    tint = CoursesUi.Purple,
                    modifier = Modifier.size(22.dp)
                )
            }

            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings",
                    tint = CoursesUi.TextPrimary
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Courses",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = CoursesUi.TextPrimary
            )

            Spacer(Modifier.width(12.dp))

            SearchPill(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(14.dp))

        val filtered = remember(query, courses) {
            if (query.isBlank()) courses
            else courses.filter { it.title.contains(query, ignoreCase = true) }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(bottom = 18.dp)
        ) {
            items(filtered, key = { it.id }) { course ->
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = course.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = CoursesUi.TextPrimary
                    )
                    CourseHeroCard(
                        course = course,
                        onStart = { onOpenCourse(course.id) }
                    )
                }
            }
        }
    }
}


@Composable
private fun SearchPill(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(50.dp),
        color = CoursesUi.SearchBg,
        shape = RoundedCornerShape(22.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = {
                    Text("Search courses...", color = Color(0xFF6E6E6E), fontSize = 13.sp)
                },
                singleLine = true,
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = CoursesUi.Purple
                )
            )

            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = Color(0xFF2B2B2B)
            )
        }
    }
}

@Composable
private fun CourseHeroCard(
    course: Course,
    onStart: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = CoursesUi.CardCorner,
        color = course.cardBg,
        border = BorderStroke(2.dp, course.border),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Box(Modifier.fillMaxSize()) {
            if (course.heroRes != null) {
                Image(
                    painter = androidx.compose.ui.res.painterResource(course.heroRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                )
            }

            // Start button bottom-right
            StartCourseButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 14.dp, bottom = 14.dp),
                onClick = onStart
            )
        }
    }
}

@Composable
private fun StartCourseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(36.dp)
            .width(132.dp)
            .clip(RoundedCornerShape(22.dp))
            .clickable { onClick() },
        color = CoursesUi.Purple,
        shape = RoundedCornerShape(22.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.PlayArrow,
                contentDescription = "Start",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Start Course",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun ModuleScreen(
    courseId: String,
    courses: List<Course> = remember { buildCourses() },
    onOpenUsefulLinks: (() -> Unit)? = null,
    onOpenModuleUrl: (String) -> Unit,
    onSettingsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
) {
    val course = remember(courseId, courses) { courses.firstOrNull { it.id == courseId } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoursesUi.PageBg)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            if (course?.heroRes != null) {
                Image(
                    painter = androidx.compose.ui.res.painterResource(course.heroRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    alpha = 0.18f
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFEFF6FF))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .padding(top = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(CoursesUi.PurpleSoft)
                        .clickable { onProfileClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Profile",
                        tint = CoursesUi.Purple,
                        modifier = Modifier.size(22.dp)
                    )
                }

                IconButton(onClick = onSettingsClick) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings",
                        tint = CoursesUi.TextPrimary
                    )
                }
            }

            // Title
            Text(
                text = course?.title ?: "Course",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = CoursesUi.TextPrimary,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(horizontal = 18.dp)
                    .padding(top = 78.dp)
            )

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 18.dp, bottom = 16.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .clickable { onOpenUsefulLinks?.invoke() },
                color = CoursesUi.Purple,
                shape = RoundedCornerShape(22.dp),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Link,
                        contentDescription = "Useful Links",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Useful Links",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val modules = (course?.modules ?: emptyList()).take(4) // max 4
            modules.forEach { module ->
                ModuleRow(
                    module = module,
                    onClick = { onOpenModuleUrl(module.url) }
                )
            }
        }
    }
}

@Composable
private fun ModuleRow(
    module: CourseModule,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(shape)
            .clickable { onClick() },
        shape = shape,
        color = CoursesUi.ModuleCardBg,
        border = BorderStroke(1.dp, CoursesUi.ModuleBorder),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Row(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(CoursesUi.PurpleSoft),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = module.index.toString(),
                        fontWeight = FontWeight.Bold,
                        color = CoursesUi.Purple,
                        fontSize = 13.sp
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = module.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = CoursesUi.TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = module.subtitle,
                        fontSize = 12.sp,
                        color = CoursesUi.TextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            val panelColor = when (module.state) {
                ModuleState.CONTINUE -> CoursesUi.ModuleGreen
                ModuleState.START -> CoursesUi.ModulePurplePanel
                ModuleState.COMPLETED -> CoursesUi.ModuleCardBg
            }

            Box(
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight()
                    .background(panelColor),
                contentAlignment = Alignment.Center
            ) {
                when (module.state) {
                    ModuleState.COMPLETED -> {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = "Completed",
                            tint = Color(0xFF2A2A2A),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    ModuleState.CONTINUE -> {
                        Icon(
                            imageVector = Icons.Outlined.PlayArrow,
                            contentDescription = "Continue",
                            tint = CoursesUi.ModuleGreenIcon,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    ModuleState.START -> {
                        Icon(
                            imageVector = Icons.Outlined.PlayArrow,
                            contentDescription = "Start",
                            tint = Color(0xFF3A3A3A),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    url: String
) {
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        },
        update = { it.loadUrl(url) }
    )
}
