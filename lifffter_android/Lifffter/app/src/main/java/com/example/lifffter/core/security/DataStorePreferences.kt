package com.example.lifffter.core.security

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import android.util.Base64

private val Context.dataStore by preferencesDataStore(
    name = "app_user_preferences"
)

// saving to dataStore and reading back from datastore
class DataStorePreferences(
    context: Context,
    val securityUtil: SecurityUtil,
    val gson: Gson
) {
    val bytesToStringSeparator = "|"
    val keyAlias = "appkey"
    val dataStore = context.dataStore
    val ivToStringSeparator = ":iv:"

    fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T):
            Flow<T> = dataStore.data.catch { exception ->
            if(exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
    }.map { preferences ->
        val result = preferences[key] ?: defaultValue
        result
    }

    suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun putSecurePreference(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            // No Gson! Just encrypt the raw string directly.
            val (iv, secureByteArray) = securityUtil.encryptData(keyAlias, value)

            val secureIv = Base64.encodeToString(iv, Base64.NO_WRAP)
            val secureByteArrayString = Base64.encodeToString(secureByteArray, Base64.NO_WRAP)
            val secureString = secureIv + ivToStringSeparator + secureByteArrayString
            preferences[key] = secureString
        }
    }

    fun getSecurePreference(
        key: Preferences.Key<String>,
        defaultValue: String
    ): Flow<String> = dataStore.data.catch { exception ->
        if(exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val secureString = preferences[key] ?: return@map defaultValue
        val (ivString, encryptedString) = secureString.split(ivToStringSeparator, limit = 2)

        val iv = Base64.decode(ivString, Base64.NO_WRAP)
        val encryptedStringByteArray = Base64.decode(encryptedString, Base64.NO_WRAP)

        // No Gson! Just return the decrypted string directly.
        val decryptedValue = securityUtil.decryptData(keyAlias, iv, encryptedStringByteArray)
        decryptedValue
    }

    suspend fun <T> removePreference(key: Preferences.Key<T>) {
        dataStore.edit {
            it.remove(key)
        }
    }

    suspend fun clearAllPreference() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}