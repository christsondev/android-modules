package com.christsondev.utilities

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.lang.reflect.Type

class StorageHelper(
    private val sharedPreferences: SharedPreferences,
    private val moshi: Moshi,
) {

    // String
    fun save(key: String, value: String) =
        sharedPreferences.edit().putString(key, value).apply()

    fun get(key: String, defaultValue: String) =
        sharedPreferences.getString(key, defaultValue)

    // Boolean
    fun save(key: String, value: Boolean) =
        sharedPreferences.edit().putBoolean(key, value).apply()

    fun get(key: String, defaultValue: Boolean) =
        sharedPreferences.getBoolean(key, defaultValue)

    // Int
    fun save(key: String, value: Int) =
        sharedPreferences.edit().putInt(key, value).apply()

    fun get(key: String, defaultValue: Int) =
        sharedPreferences.getInt(key, defaultValue)

    // Long
    fun save(key: String, value: Long) =
        sharedPreferences.edit().putLong(key, value).apply()

    fun get(key: String, defaultValue: Long) =
        sharedPreferences.getLong(key, defaultValue)

    // Float
    fun save(key: String, value: Float) =
        sharedPreferences.edit().putFloat(key, value).apply()

    fun get(key: String, defaultValue: Float) =
        sharedPreferences.getFloat(key, defaultValue)

    // Object
    fun <T> get(key: String, type: Type, defaultValue: T) =
        runCatching {
            val jsonString = get(key, "").orEmpty()
            moshi.adapter<T>(type).fromJson(jsonString) ?: defaultValue
        }.getOrDefault(defaultValue)

    fun <T> save(key: String, type: Type, value: T) =
        save(key, moshi.adapter<T>(type).toJson(value))

    fun asFlow(key: String, defaultValue: String) = callbackFlow {
        // Create a listener for preference changes
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                trySend(sharedPreferences.getString(key, defaultValue) ?: defaultValue)
            }
        }
        // Register the listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        // Emit the initial value
        trySend(sharedPreferences.getString(key, defaultValue) ?: defaultValue)
        // Unregister the listener when the flow is closed
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }
}