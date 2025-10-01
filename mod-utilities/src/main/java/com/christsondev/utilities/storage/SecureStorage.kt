package com.christsondev.utilities.storage

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.config.TinkConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager

class SecureStorage(
    context: Context,
    masterKeyUri: String,
    keysetName: String,
    fileName: String,
) {

    private val aead: Aead = try {
        // 1. Register Tink Configuration
        TinkConfig.register()

        // 2. Build the KeysetHandle, which uses Android Keystore for the master key
        AndroidKeysetManager.Builder()
            .withKeyTemplate(KeyTemplates.get("AES256_GCM")) // The encryption algorithm
            .withSharedPref(context, keysetName, fileName)
            .withMasterKeyUri(masterKeyUri)
            .build()
            .keysetHandle
            .getPrimitive(RegistryConfiguration.get(), Aead::class.java)
    } catch (e: Exception) {
        // Handle initialization errors (e.g., Keystore issues)
        throw IllegalStateException("Failed to initialize Tink Aead", e)
    }

    /**
     * Encrypts the plaintext string using the Aead primitive.
     */
    fun encrypt(plaintext: String): ByteArray {
        val plaintextBytes = plaintext.toByteArray(Charsets.UTF_8)
        // Additional Associated Data (AAD) is optional, used for binding data to a context
        return aead.encrypt(plaintextBytes, null)
    }

    /**
     * Decrypts the ciphertext byte array back into a string.
     */
    fun decrypt(ciphertext: ByteArray): String {
        val decryptedBytes = aead.decrypt(ciphertext, null)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}