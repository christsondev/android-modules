package com.christsondev.utilities.storage

import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SecureStorageHelper(
    private val dataStore: DataStore<Preferences>,
    private val secureStorage: SecureStorage, // Handles Tink encryption/decryption
) {
    // --- Core Save Logic (Generic) ---

    private suspend fun <T> saveEncrypted(keyName: String, value: T) {
        // Create the string key dynamically
        val key = stringPreferencesKey(keyName)

        // 1. Convert value to String for encryption
        val plaintext = value.toString()

        // 2. Encrypt the string
        val encryptedPasswordBytes = secureStorage.encrypt(plaintext)

        // 3. Encode the encrypted bytes to a Base64 String for DataStore
        val encryptedBase64 = Base64.encodeToString(encryptedPasswordBytes, Base64.DEFAULT)

        dataStore.edit { preferences ->
            preferences[key] = encryptedBase64
        }
    }

    // --- Core Read Logic (Generic) ---

    private fun <T> getDecrypted(
        keyName: String,
        defaultValue: T,
        converter: (String) -> T
    ): Flow<T> {
        // Create the string key dynamically
        val key = stringPreferencesKey(keyName)

        return dataStore.data
            .map { preferences ->
                val encryptedBase64 = preferences[key] ?: ""

                if (encryptedBase64.isNotEmpty()) {
                    val encryptedBytes = Base64.decode(encryptedBase64, Base64.DEFAULT)
                    try {
                        // 1. Decrypt the byte array to get the original string
                        val decryptedString = secureStorage.decrypt(encryptedBytes)

                        // 2. Convert the string back to the desired type
                        return@map converter(decryptedString)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        // Return default value on decryption failure
                        return@map defaultValue
                    }
                } else {
                    return@map defaultValue
                }
            }
    }

    // #######################################################################
    // ############################# PUBLIC API ##############################
    // #######################################################################

    // --- STRING (e.g., saveString("auth_token", "jwt123")) ---

    suspend fun saveString(key: String, value: String) =
        saveEncrypted(key, value)

    fun getStringFlow(key: String, defaultValue: String): Flow<String> =
        getDecrypted(key, defaultValue) { it }


    // --- BOOLEAN (e.g., saveBoolean("is_premium_user", true)) ---

    suspend fun saveBoolean(key: String, value: Boolean) =
        saveEncrypted(key, value)

    fun getBooleanFlow(key: String, defaultValue: Boolean): Flow<Boolean> =
        getDecrypted(key, defaultValue) { it.toBoolean() }


    // --- INTEGER (e.g., saveInt("login_attempts", 3)) ---

    suspend fun saveInt(key: String, value: Int) =
        saveEncrypted(key, value)

    fun getIntFlow(key: String, defaultValue: Int): Flow<Int> =
        getDecrypted(key, defaultValue) { it.toIntOrNull() ?: defaultValue }


    // --- FLOAT (e.g., saveFloat("app_version", 2.1f)) ---

    suspend fun saveFloat(key: String, value: Float) =
        saveEncrypted(key, value)

    fun getFloatFlow(key: String, defaultValue: Float): Flow<Float> =
        getDecrypted(key, defaultValue) { it.toFloatOrNull() ?: defaultValue }
}