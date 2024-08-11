package com.project.mentalhealth.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "accountPreferences")

class AccountPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    fun getIsLoggedIn() = dataStore.data.map { it[isLoggedInPreferences] ?: false }
    fun getUsername() = dataStore.data.map { it[usernamePreferences] ?: preferencesDefaultValue }

    suspend fun savePreferences(
        username: String,
        isLoggedIn: Boolean
    ) {
        dataStore.edit { prefs ->
            prefs[usernamePreferences] = username
            prefs[isLoggedInPreferences] = isLoggedIn
        }
    }

    suspend fun clearPreferences() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AccountPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) = INSTANCE ?: synchronized(this) {
            val instance = AccountPreferences(dataStore)
            INSTANCE = instance
            instance
        }

        val isLoggedInPreferences = booleanPreferencesKey("isLoggedInPreferences")
        val usernamePreferences = stringPreferencesKey("usernamePreferences")
        const val preferencesDefaultValue: String = "preferences_default_value"
    }
}