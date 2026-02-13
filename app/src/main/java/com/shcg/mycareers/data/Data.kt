package com.shcg.mycareers.data

import androidx.compose.ui.graphics.Color
import com.shcg.mycareers.R


/* == DATA == */
data class Course(
    val id: Int, // unique identifier for each course
    val name: String, // Name of the course
    val imageRes: Int, // Banner image for each course
    val colourCourse: Color, // Colour assigned to each course.
    val secColourCourse: Color,
//    val isContinue: Boolean, // Course completion status - Continue - so if one or more of the modules inside of the course have isComplted, then isContinue is assigned to true.
//    val isCompleted: Boolean // Course completion status - if it is completed then corresponding button colours apply.
)
data class Module(
    val id: Int,  // Unique identifier for each module.
    val courseId: Int,  // Indeitifies which course the module relates to.
    val name: String, //Name of the module
    val url: String? = null, // URL for the WebView
    var isCompleted: Boolean = false, // module completion stauts - if it is completed then it's badge shows in the profile page.
    val imageRes: Int // Badge Icon
)



/* == COURSES == */
val courseItems = listOf( // Each course
    Course(1, "Maritime and Logistics", R.drawable.maritime, Color(32, 183, 255), Color(176, 228, 249)),
    Course(2, "Health and Social Care", R.drawable.health, Color(45, 216, 124), Color(210, 243, 222)),
    Course(3, "Creative Arts", R.drawable.creative, Color(227, 119, 142), Color(238, 206, 211)),
    Course(4, "Hospitality", R.drawable.hospitality, Color(243, 184, 66), Color(245, 228, 185)),
    Course(5, "Construction", R.drawable.construction, Color(172, 133, 216), Color(221, 212, 233)),
    Course(6, "Digital Technologies", R.drawable.digital, Color(197, 91, 225), Color(232, 197, 239))
)


/* == COURSE MODULES == */
val maritimeModules = listOf(
    Module(101, 1,  "An introduction to Logistics", "https://mycareers.uk/maritime-and-logistics-courses/an-introduction-to-logistics/", true, R.drawable.intro_to_logistics),
    Module(102, 1,  "The supply chain", "https://mycareers.uk/maritime-and-logistics-courses/the-supply-chain/", true, R.drawable.supply_chain),
    Module(103, 1,  "Import-export", "https://mycareers.uk/maritime-and-logistics-courses/import-export/", true, R.drawable.import_export),
    Module(104, 1,  "Green logistics", "https://mycareers.uk/maritime-and-logistics-courses/green-logistics/", false, R.drawable.green_logistics)
)

val healthModules = listOf(
    Module(201, 2,  "An Introduction to Nursing", "https://mycareers.uk/health-and-social-courses/an-introduction-to-nursing/", false, R.drawable.intro_to_nursing),
    Module(202, 2,  "Residential Social Care", "https://mycareers.uk/health-and-social-courses/an-introduction-to-residential-social-care/", false, R.drawable.residential_socialcare),
    Module(203, 2,  "Community Social Care", "https://mycareers.uk/health-and-social-courses/an-introduction-to-community-social-care/", false, R.drawable.community_socialcare),
    Module(204, 2,  "Allied Health", "https://mycareers.uk/health-and-social-courses/an-introduction-to-allied-health/", false, R.drawable.allied_health)
)

val creativeModules = listOf(
    Module(301, 3,  "Stage Management", "https://mycareers.uk/creative-arts-courses/stage-management/", false, R.drawable.stage_management),
    Module(302, 3,  "Lighting, Design & Operation", "https://mycareers.uk/creative-arts-courses/lighting-design/", false, R.drawable.lighting_design_operation),
    Module(303, 3,  "Sound Design and Operation", "https://mycareers.uk/creative-arts-courses/sound-design/", false, R.drawable.sound_design_operation),
    Module(304, 3,  "Set and Properties Design", "https://mycareers.uk/creative-arts-courses/set-properties-design/", false, R.drawable.set_properties_design)
)

val hospitalityModules = listOf(
    Module(401, 4,  "An Introduction to Hospitality", "https://mycareers.uk/hospitality-courses/an-introduction-to-the-hospitality-industry/", false, R.drawable.intro_to_hospitality)
)

val constructionModules = listOf(
    Module(501, 5,  "Intro to Civil Enginerring", "https://mycareers.uk/construction-courses/an-introduction-to-civil-engineering/", false, R.drawable.intro_to_construction),
    Module(502, 5,  "Plastering, Brick and Carpentry", "https://mycareers.uk/construction-courses/plastering-brickwork-carpentry/", false, R.drawable.intro_to_plastering_brick_carpentry),
    Module(503, 5,  "Plumbing and Electrical", "https://mycareers.uk/construction-courses/plumbing-electrical/", false, R.drawable.intro_to_plumbing_electrical)
)

val digitalModules = listOf(
    Module(0, 6,  "Digital Foundations", "https://mycareers.uk/digital-technology-courses/digital-foundations", false, R.drawable.placeholder),
    Module(0, 6,  "Digital Technology", "https://mycareers.uk/digital-technology-courses/digital-technology", false, R.drawable.placeholder),
    Module(0, 6,  "Digital Creative", "https://mycareers.uk/digital-technology-courses/digital-creative", false, R.drawable.placeholder),
    Module(0, 6,  "Innovation and Emerging Tech", "https://mycareers.uk/digital-technology-courses/innovation-and-emerging-tech", false, R.drawable.placeholder)
)

val moduleList = maritimeModules + healthModules + creativeModules + hospitalityModules + constructionModules + digitalModules

val modulesByCourseId = mapOf(
    1 to maritimeModules,
    2 to healthModules,
    3 to creativeModules,
    4 to hospitalityModules,
    5 to constructionModules,
    6 to digitalModules
)

fun courseModules(courseId: Int): List<Module> =
    moduleList.filter { it.courseId == courseId }

fun isCourseCompleted(courseId: Int): Boolean {
    val modules = courseModules(courseId)
    return modules.isNotEmpty() && modules.all { it.isCompleted }
}

fun isCourseContinue(courseId: Int): Boolean {
    val modules = courseModules(courseId)
    val completed = modules.any { it.isCompleted }
    return completed
}