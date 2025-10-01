package com.christsondev.utilities.storage

import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type

class SecureStorageHelper(
    private val dataStore: DataStore<Preferences>,
    private val secureStorage: SecureStorage, // Handles Tink encryption/decryption
    private val moshi: Moshi,
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

    // --- STRING ---

    suspend fun save(key: String, value: String) =
        saveEncrypted(key, value)

    fun get(key: String, defaultValue: String): Flow<String> =
        getDecrypted(key, defaultValue) { it }


    // --- BOOLEAN ---

    suspend fun save(key: String, value: Boolean) =
        saveEncrypted(key, value)

    fun get(key: String, defaultValue: Boolean): Flow<Boolean> =
        getDecrypted(key, defaultValue) { it.toBoolean() }


    // --- INTEGER ---

    suspend fun save(key: String, value: Int) =
        saveEncrypted(key, value)

    fun get(key: String, defaultValue: Int): Flow<Int> =
        getDecrypted(key, defaultValue) { it.toIntOrNull() ?: defaultValue }

    // --- LONG ---

    suspend fun save(key: String, value: Long) =
        saveEncrypted(key, value)

    fun get(key: String, defaultValue: Long): Flow<Long> =
        getDecrypted(key, defaultValue) { it.toLongOrNull() ?: defaultValue }


    // --- FLOAT ---

    suspend fun save(key: String, value: Float) =
        saveEncrypted(key, value)

    fun get(key: String, defaultValue: Float): Flow<Float> =
        getDecrypted(key, defaultValue) { it.toFloatOrNull() ?: defaultValue }

    // --- OBJECT ---

    suspend fun <T : Any> save(key: String, type: Type, value: T) =
        saveString(key, moshi.adapter<T>(type).toJson(value))

    fun <T> get(key: String, type: Type, defaultValue: T) =
        runCatching {
            getDecrypted(key, defaultValue) { jsonString ->
                moshi.adapter<T>(type).fromJson(jsonString) ?: defaultValue
            }
        }.getOrDefault(defaultValue)

}