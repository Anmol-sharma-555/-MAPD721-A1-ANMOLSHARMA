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

class StoreUserData(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserEmail")

        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    val getName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }
    val getEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }

    val getId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }

    suspend fun saveData(name: String, email: String, id: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_ID_KEY] = id
        }
    }

    suspend fun loadData(): Triple<String?, String?, String?> {
        val preferences = context.dataStore.data.first()
        return Triple(
            preferences[USER_NAME_KEY],
            preferences[USER_EMAIL_KEY],
            preferences[USER_ID_KEY]
        )
    }

    suspend fun clearData() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_NAME_KEY)
            preferences.remove(USER_EMAIL_KEY)
            preferences.remove(USER_ID_KEY)
        }
    }
}
