package com.christsondev.utilities

import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.deleteIfExists
import kotlin.io.path.outputStream

class AppDirectory(
    private val context: Context,
    private val rootDir: String,
) {
    fun getExternalFilePath(fileName: String): Path {
        val parent = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), rootDir)
        if (!parent.exists()) {
            parent.mkdirs()
        }
        return File(parent, fileName).toPath()
    }

    fun readFromFile(fileName: String, onNext: (String) -> Unit) {
        val path = getExternalFilePath(fileName)
        Files.lines(path).use { lines ->
            lines.forEach { line ->
                onNext.invoke(line)
            }
        }
    }

    fun copyAssetToDirectory(assetFileName: String) {
        val path = getExternalFilePath(assetFileName)
        val inputStream = context.assets.open(assetFileName)
        Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING)
    }

    /**
     * Convert uri that start with content:// into Path
     */
    fun uriToPath(uri: Uri): Path {
        val path = uri.getFileOrTemp()
        context.contentResolver.openInputStream(uri)?.use { input ->
            path.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return path
    }

    private fun Uri.getFileOrTemp() =
        this.getFilename(context)?.let { fileName ->
            val path = getExternalFilePath(fileName)
            path.deleteIfExists()
            return path
        } ?: kotlin.io.path.createTempFile()
}