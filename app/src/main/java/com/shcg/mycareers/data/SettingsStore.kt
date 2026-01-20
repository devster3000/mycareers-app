package com.shcg.mycareers.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object SettingsKeys {
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val NAME = stringPreferencesKey("name")
}

//fun darkModeFlow(context: Context): Flow<Boolean> =
//    context.dataStore.data.map { prefs ->
//        prefs[SettingsKeys.DARK_MODE] ?: false
//    }
//
//fun nameFlow(context: Context): Flow<String> =
//    context.dataStore.data.map { prefs ->
//        prefs[SettingsKeys.NAME] ?: "John Doe"
//    }
//
//suspend fun setDarkMode(context: Context, enabled: Boolean) {
//    context.dataStore.edit { prefs ->
//        prefs[SettingsKeys.DARK_MODE] = enabled
//    }
//}
//
//suspend fun setName(context: Context, name: String) {
//    context.dataStore.edit { prefs ->
//        prefs[SettingsKeys.NAME] = name
//    }
//}
