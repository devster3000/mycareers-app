package com.shcg.mycareers.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



// One DataStore for the whole app
private val Context.dataStore by preferencesDataStore(name = "mycareers_settings")

// Keys
private val KEY_DARK_MODE = booleanPreferencesKey("dark_mode")
private val FOLLOW_SYSTEM_KEY = booleanPreferencesKey("follow_system")

private val KEY_DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
private val KEY_NAME = stringPreferencesKey("profile_name")
private val KEY_FAVOURITES = stringPreferencesKey("favourites")
private val KEY_BADGES = stringPreferencesKey("badges")

// Settings -> Dark mode

fun followSystemFlow(context: Context) =
    context.dataStore.data.map { prefs -> prefs[FOLLOW_SYSTEM_KEY] ?: true }

suspend fun setFollowSystem(context: Context, enabled: Boolean) {
    context.dataStore.edit { prefs ->
        prefs[FOLLOW_SYSTEM_KEY] = enabled
    }
}

fun darkModeFlow(context: Context): Flow<Boolean> =
    context.dataStore.data.map { prefs -> prefs[KEY_DARK_MODE] ?: false }

suspend fun setDarkMode(context: Context, enabled: Boolean) {
    context.dataStore.edit { prefs -> prefs[KEY_DARK_MODE] = enabled }
}

// Dynamic Colours

fun dynamicColorFlow(context: Context): Flow<Boolean> =
    context.dataStore.data.map { prefs -> prefs[KEY_DYNAMIC_COLOR] ?: true } // default ON

suspend fun setDynamicColor(context: Context, enabled: Boolean) {
    context.dataStore.edit { prefs -> prefs[KEY_DYNAMIC_COLOR] = enabled }
}

// Settings Page -> Name

fun nameFlow(context: Context): Flow<String> =
    context.dataStore.data.map { prefs -> prefs[KEY_NAME] ?: "Gordon Freeman" }

suspend fun setName(context: Context, name: String) {
    val cleaned = name.trim().ifBlank { "Gordon Freeman" }
    context.dataStore.edit { prefs -> prefs[KEY_NAME] = cleaned }
}

// Home -> Favourite Courses

fun favouritesFlow(context: Context): Flow<Set<String>> =
    context.dataStore.data.map { prefs ->
        val csv = prefs[KEY_FAVOURITES].orEmpty()
        if (csv.isBlank()) emptySet()
        else csv.split(",").map { it.trim() }.filter { it.isNotBlank() }.toSet()
    }

suspend fun setFavourites(context: Context, favourites: Set<String>) {
    context.dataStore.edit { prefs ->
        prefs[KEY_FAVOURITES] = favourites.joinToString(",")
    }
}

suspend fun toggleFavourite(context: Context, courseId: String) {
    context.dataStore.edit { prefs ->
        val current = prefs[KEY_FAVOURITES].orEmpty()
            .split(",")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .toMutableSet()

        if (current.contains(courseId)) current.remove(courseId) else current.add(courseId)

        prefs[KEY_FAVOURITES] = current.joinToString(",")
    }
}

object BadgeStore {
    private val EARNED_BADGES = stringSetPreferencesKey("earned_badges")

    fun earnedBadgeIds(context: Context): Flow<Set<Int>> =
        context.dataStore.data.map { prefs ->
            prefs[EARNED_BADGES]
                ?.mapNotNull { it.toIntOrNull() }
                ?.toSet()
                ?: emptySet()
        }

    suspend fun awardBadge(context: Context, moduleId: Int) {
        context.dataStore.edit { prefs ->
            val current = prefs[EARNED_BADGES] ?: emptySet()
            prefs[EARNED_BADGES] = current + moduleId.toString()
        }
    }

    suspend fun removeBadge(context: Context, moduleId: Int) {
        context.dataStore.edit { prefs ->
            val current = prefs[EARNED_BADGES] ?: emptySet()
            prefs[EARNED_BADGES] = current - moduleId.toString()
        }
    }
}