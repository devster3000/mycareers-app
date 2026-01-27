package com.shcg.mycareers.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//private val KEY_FAVS = stringSetPreferencesKey("favourite_courses")

//fun favouritesFlow(context: Context): Flow<Set<String>> =
//    context.dataStore.data.map { prefs -> prefs[KEY_FAVS] ?: emptySet() }
//
//suspend fun toggleFavourite(context: Context, courseId: String) {
//    context.dataStore.edit { prefs ->
//        val current = prefs[KEY_FAVS] ?: emptySet()
//        prefs[KEY_FAVS] = if (current.contains(courseId)) current - courseId else current + courseId
//    }
//}
