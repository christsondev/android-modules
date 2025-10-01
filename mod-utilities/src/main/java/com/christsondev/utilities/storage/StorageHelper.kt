package com.christsondev.utilities.storage

import android.content.SharedPreferences
import androidx.core.content.edit
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
        sharedPreferences.edit { putString(key, value) }

    fun get(key: String, defaultValue: String?) =
        sharedPreferences.getString(key, defaultValue)

    // Boolean
    fun save(key: String, value: Boolean) =
        sharedPreferences.edit { putBoolean(key, value) }

    fun get(key: String, defaultValue: Boolean) =
        sharedPreferences.getBoolean(key, defaultValue)

    // Int
    fun save(key: String, value: Int) =
        sharedPreferences.edit { putInt(key, value) }

    fun get(key: String, defaultValue: Int) =
        sharedPreferences.getInt(key, defaultValue)

    // Long
    fun save(key: String, value: Long) =
        sharedPreferences.edit { putLong(key, value) }

    fun get(key: String, defaultValue: Long) =
        sharedPreferences.getLong(key, defaultValue)

    // Float
    fun save(key: String, value: Float) =
        sharedPreferences.edit { putFloat(key, value) }

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

    // region Flow

    fun asFlow(key: String, defaultValue: String) = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                trySend(sharedPreferences.getString(key, defaultValue))
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        trySend(sharedPreferences.getString(key, defaultValue))
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    fun asFlow(key: String, defaultValue: Boolean) = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                trySend(sharedPreferences.getBoolean(key, defaultValue))
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        trySend(sharedPreferences.getBoolean(key, defaultValue))
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    fun asFlow(key: String, defaultValue: Int) = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                trySend(sharedPreferences.getInt(key, defaultValue))
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        trySend(sharedPreferences.getInt(key, defaultValue))
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    fun asFlow(key: String, defaultValue: Long) = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                trySend(sharedPreferences.getLong(key, defaultValue))
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        trySend(sharedPreferences.getLong(key, defaultValue))
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    fun asFlow(key: String, defaultValue: Float) = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                trySend(sharedPreferences.getFloat(key, defaultValue))
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        trySend(sharedPreferences.getFloat(key, defaultValue))
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    fun<T> asFlow(key: String, type: Type, defaultValue: T) = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                trySend(get(key, type, defaultValue))
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        trySend(get(key, type, defaultValue))
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    // endregion Flow
}