package com.shcg.mycareers.data
import com.shcg.mycareers.R

data class Course(
    val id: Int,
    val name: String,
    val imageRes: Int
)

data class Module(
    val id: Int,
    val name: String,
    val url: String? = null
)

val courseItems = listOf(
    Course(0, "Maritime and Logistics", R.drawable.maritime),
    Course(1, "Health and Social Care", R.drawable.health),
    Course(2, "Creative Arts", R.drawable.creative),
    Course(3, "Hospitality", R.drawable.hospitality),
    Course(4, "Construction", R.drawable.construction),
    Course(5, "Digital Technologies", R.drawable.digital)
)


/* == COURSE MODULES == */
val maritimeModules = listOf(
    Module(0, "An introduction to Logistics", "https://mycareers.uk/maritime-and-logistics-courses/an-introduction-to-logistics/"),
    Module(1, "The supply chain", "https://mycareers.uk/maritime-and-logistics-courses/the-supply-chain/"),
    Module(2, "Import-export", "https://mycareers.uk/maritime-and-logistics-courses/import-export/"),
    Module(3, "Green logistics", "https://mycareers.uk/maritime-and-logistics-courses/green-logistics/")
)

val healthModules = listOf(
    Module(4, "An Introduction to Nursing", "https://mycareers.uk/health-and-social-courses/an-introduction-to-nursing/"),
    Module(5, "Residential Social Care", "https://mycareers.uk/health-and-social-courses/an-introduction-to-residential-social-care/"),
    Module(6, "Community Social Care", "https://mycareers.uk/health-and-social-courses/an-introduction-to-residential-social-care/"),
    Module(7, "Allied Health", "https://mycareers.uk/health-and-social-courses/an-introduction-to-allied-health/")
)

val creativeModules = listOf(
    Module(8, "Stage Management", "https://mycareers.uk/creative-arts-courses/stage-management/"),
    Module(9, "Lighting, Design & Operation", "https://mycareers.uk/creative-arts-courses/stage-management/"),
    Module(10, "Sound Design and Operation", "https://mycareers.uk/creative-arts-courses/sound-design/"),
    Module(11, "Set and Properties Design", "https://mycareers.uk/creative-arts-courses/sound-design/")
)

val hospitalityModules = listOf(
    Module(12, "An Introduction to Hospitality", "https://mycareers.uk/hospitality-courses/an-introduction-to-the-hospitality-industry/")
)

val constructionModules = listOf(
    Module(13, "Intro to Civil Enginerring", "https://mycareers.uk/construction-courses/an-introduction-to-civil-engineering/"),
    Module(14, "Plastering, Brick and Carpentry", "https://mycareers.uk/construction-courses/plastering-brickwork-carpentry/"),
    Module(15, "Plumbing and Electrical", "https://mycareers.uk/construction-courses/plumbing-electrical/")
)

val digitalModules = listOf(
    Module(16, "Digital Foundations", "https://shcg.ac.uk/"),
    Module(17, "Digital Technology", "https://shcg.ac.uk/"),
    Module(18, "Digital Creative", "https://shcg.ac.uk/"),
    Module(19, "Innovation and Emerging Tech", "https://shcg.ac.uk/")
)
