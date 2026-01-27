package com.shcg.mycareers.data

import com.shcg.mycareers.R

data class Course(
    val id: Int, // unique identifier for each course
    val name: String, // Name of the course
    val imageRes: Int // Banner image for each course
)

data class Module(
    val id: Int, // Unique identifier for each module.
    val name: String, //Name of the module
    val url: String? = null, // URL for the WebView
    var isCompleted: Boolean = false, // if the module is completed, then the badge will show in "Badges" section
    val imageRes: Int // Badge Icon
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
    Module(0, "An introduction to Logistics", "https://mycareers.uk/maritime-and-logistics-courses/an-introduction-to-logistics/", false, R.drawable.intro_to_logistics),
    Module(1, "The supply chain", "https://mycareers.uk/maritime-and-logistics-courses/the-supply-chain/", false, R.drawable.supply_chain),
    Module(2, "Import-export", "https://mycareers.uk/maritime-and-logistics-courses/import-export/", false, R.drawable.import_export),
    Module(3, "Green logistics", "https://mycareers.uk/maritime-and-logistics-courses/green-logistics/", false, R.drawable.green_logistics)
)

val healthModules = listOf(
    Module(4, "An Introduction to Nursing", "https://mycareers.uk/health-and-social-courses/an-introduction-to-nursing/", false, R.drawable.intro_to_nursing),
    Module(5, "Residential Social Care", "https://mycareers.uk/health-and-social-courses/an-introduction-to-residential-social-care/", false, R.drawable.residential_socialcare),
    Module(6, "Community Social Care", "https://mycareers.uk/health-and-social-courses/an-introduction-to-community-social-care/", false, R.drawable.community_socialcare),
    Module(7, "Allied Health", "https://mycareers.uk/health-and-social-courses/an-introduction-to-allied-health/", false, R.drawable.allied_health)
)

val creativeModules = listOf(
    Module(8, "Stage Management", "https://mycareers.uk/creative-arts-courses/stage-management/", false, R.drawable.stage_management),
    Module(9, "Lighting, Design & Operation", "https://mycareers.uk/creative-arts-courses/lighting-design/", false, R.drawable.lighting_design_operation),
    Module(10, "Sound Design and Operation", "https://mycareers.uk/creative-arts-courses/sound-design/", false, R.drawable.sound_design_operation),
    Module(11, "Set and Properties Design", "https://mycareers.uk/creative-arts-courses/set-properties-design/", false, R.drawable.set_properties_design)
)

val hospitalityModules = listOf(
    Module(12, "An Introduction to Hospitality", "https://mycareers.uk/hospitality-courses/an-introduction-to-the-hospitality-industry/", false, R.drawable.intro_to_hospitality)
)

val constructionModules = listOf(
    Module(13, "Intro to Civil Enginerring", "https://mycareers.uk/construction-courses/an-introduction-to-civil-engineering/", false, R.drawable.intro_to_construction),
    Module(14, "Plastering, Brick and Carpentry", "https://mycareers.uk/construction-courses/plastering-brickwork-carpentry/", false, R.drawable.intro_to_plastering_brick_carpentry),
    Module(15, "Plumbing and Electrical", "https://mycareers.uk/construction-courses/plumbing-electrical/", false, R.drawable.intro_to_plumbing_electrical)
)

val digitalModules = listOf(
    Module(16, "Digital Foundations", "https://shcg.ac.uk/", false, R.drawable.placeholder),
    Module(17, "Digital Technology", "https://shcg.ac.uk/", false, R.drawable.placeholder),
    Module(18, "Digital Creative", "https://shcg.ac.uk/", false, R.drawable.placeholder),
    Module(19, "Innovation and Emerging Tech", "https://shcg.ac.uk/", false, R.drawable.placeholder)
)
