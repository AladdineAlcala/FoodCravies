package com.jtautomation02.foodcravies.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Create the DataStore instance via a delegate
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")

@Singleton
class SessionRepository @Inject constructor(
    @ApplicationContext private val  context: Context
) {
    // Define the key for storing the user token
    private object PreferencesKeys {
        val USER_TOKEN = stringPreferencesKey("user_token")
        val TOKEN_EXPIRY_TIMESTAMP = longPreferencesKey("token_expiry_timestamp")
        // --- NEW KEY ---
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    // Expose a Flow that emits the user token (or null if not logged in)
    val userTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            val token = preferences[PreferencesKeys.USER_TOKEN]
            val expiryTimestamp = preferences[PreferencesKeys.TOKEN_EXPIRY_TIMESTAMP] ?: 0L

            // Check if the token exists and is not expired
            if (!token.isNullOrEmpty() && System.currentTimeMillis() < expiryTimestamp) {
                // If valid, return the token itself
                token
            } else {
                // Otherwise, return null
                null
            }
        }

    /**
     * Retrieves the current refresh token, if one exists.
     * This is a 'first-take' utility function for the network layer.
     */
    val refreshTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.REFRESH_TOKEN]
        }


    /**
     * Saves the entire user session.
     * @param token The API access token.
     * @param refreshToken The API refresh token.
     * @param expiresInSeconds The number of seconds from 'now' until the access token expires.
     */
    suspend fun saveUserSession(token: String, refreshToken: String, expiresInSeconds: Long) {
        val expiryTimestamp = System.currentTimeMillis() + (expiresInSeconds * 1000)
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_TOKEN] = token
            preferences[PreferencesKeys.REFRESH_TOKEN] = refreshToken // Save the refresh token
            preferences[PreferencesKeys.TOKEN_EXPIRY_TIMESTAMP] = expiryTimestamp
        }
    }

    /**
     * Clears all session data from DataStore (for logout).
     */
    suspend fun clearUserSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.USER_TOKEN)
            preferences.remove(PreferencesKeys.TOKEN_EXPIRY_TIMESTAMP)
            preferences.remove(PreferencesKeys.REFRESH_TOKEN) // Clear the refresh token
        }
    }
}