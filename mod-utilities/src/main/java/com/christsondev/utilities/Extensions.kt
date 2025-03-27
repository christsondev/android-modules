package com.christsondev.utilities

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull

fun Uri.getFilename(context: Context) =
    context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
        cursor.moveToFirst()
        cursor.getStringOrNull(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }

inline fun <T> T?.orElse(defaultValue: () -> T): T {
    return this ?: defaultValue()
}

inline fun<T> String.ifNotBlank(block: (String) -> T): T? {
    return if (this.isNotBlank()) {
        block.invoke(this)
    } else {
        null
    }
}