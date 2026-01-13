package com.shcg.mycareers.ui.screens.course

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shcg.mycareers.data.Course
import com.shcg.mycareers.data.Module
import com.shcg.mycareers.data.courseItems
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.viewinterop.AndroidView
import com.shcg.mycareers.data.constructionModules
import com.shcg.mycareers.data.creativeModules
import com.shcg.mycareers.data.digitalModules
import com.shcg.mycareers.data.healthModules
import com.shcg.mycareers.data.hospitalityModules
import com.shcg.mycareers.data.maritimeModules

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun CourseScreen(onCourseClick: (Course) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(courseItems) { course ->
            CourseCard(course = course, onClick = { onCourseClick(course) })
        }
    }
}

@Composable
fun CourseCard(course: Course, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
    ) {
        Image(
            painter = painterResource(id = course.imageRes),
            contentDescription = course.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Button(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        ) {
            Text("Start Course")
        }
    }
}


@Composable
fun ModuleScreen(course: Course, navController: NavController) {
    val modules = when (course.id) {
        0 -> maritimeModules
        1 -> healthModules
        2 -> creativeModules
        3 -> hospitalityModules
        4 -> constructionModules
        5 -> digitalModules
        else -> listOf()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // ===== HEADER =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            // Background course image
            Image(
                painter = painterResource(id = course.imageRes),
                contentDescription = course.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Overlay to dim image for text visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x55000000))
            )

            // Course Title
            Text(
                text = course.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )

            // Useful Links Button
            Button(
                onClick = { /* TODO: Handle Useful Links */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text(text = "Useful Links", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== MODULE LIST =====
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(modules) { index, module ->
                ModuleCard(
                    index = index + 1,
                    module = module,
                    onClick = {
                        module.url?.let { url ->
                            val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                            navController.navigate("webview/$encodedUrl")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ModuleCard(index: Int, module: Module, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 12.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Number Circle
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFD0BCFF))
        ) {
            Text(text = index.toString(), fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Module info
        Column(modifier = Modifier.weight(1f)) {
            Text(text = module.name, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Text(
                text = getModuleStatusText(index),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        // Action button
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(getModuleActionColor(index))
        ) {
            Text(
                text = getModuleActionIcon(index),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

// ===== Sample logic for module status & icon =====
fun getModuleStatusText(index: Int): String = when(index) {
    1 -> "Completed!"
    2 -> "Continue..."
    else -> "Start now!"
}

fun getModuleActionIcon(index: Int): String = when(index) {
    1 -> "✓" // Completed
    else -> "▶" // Start / Continue
}

fun getModuleActionColor(index: Int): Color = when(index) {
    1 -> Color.LightGray
    2 -> Color(0xFFB5EAEA) // greenish for continue
    else -> Color(0xFFD0BCFF) // purple for start
}
