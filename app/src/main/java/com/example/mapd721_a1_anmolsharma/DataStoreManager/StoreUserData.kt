package com.example.mapd721_a1_anmolsharma.DataStoreManager


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Class responsible for managing user data using DataStore
class StoreUserData(private val context: Context) {

 // Companion object containing keys for user data
    companion object {
        // DataStore instance for storing user data
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserEmail")

        // Keys for user data in DataStore
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    // Flow to retrieve user name from DataStore
    val getName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }

    // Flow to retrieve user email from DataStore
    val getEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }

    // Flow to retrieve user ID from DataStore
    val getId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }

    // Function to save user data to DataStore
    suspend fun saveData(name: String, email: String, id: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_ID_KEY] = id
        }
    }

    // Function to load user data from DataStore
    suspend fun loadData(): Triple<String?, String?, String?> {
        val preferences = context.dataStore.data.first()
        return Triple(
            preferences[USER_NAME_KEY],
            preferences[USER_EMAIL_KEY],
            preferences[USER_ID_KEY]
        )
    }

    // Function to Clear user data from DataStore
    suspend fun clearData() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_NAME_KEY)
            preferences.remove(USER_EMAIL_KEY)
            preferences.remove(USER_ID_KEY)
        }
    }
}
