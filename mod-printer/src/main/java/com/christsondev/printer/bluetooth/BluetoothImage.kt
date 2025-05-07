package com.christsondev.printer.bluetooth

import android.graphics.Bitmap
import android.graphics.Canvas

/**
 * Printer paper size max width 0~550,
 * so scale the image to 550 width before printing
 */
internal class BluetoothImage(bitmap: Bitmap) {

    private var width: Int = 550
    private var height: Int = 10 * width
    private var length: Int = 20

    private var printBitmap: Bitmap
    private var bitBuffer: ByteArray
    private var imgBuffer: ByteArray

    init {
        val canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(canvasBitmap)
        canvas.drawColor(-1)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        if (length < bitmap.height) {
            length += bitmap.height
        }

        printBitmap = Bitmap.createBitmap(canvasBitmap, 0, 0, width, length)
        bitBuffer = ByteArray(width / 8)
        imgBuffer = ByteArray(width / 8 * length + 8).apply {
            this[0] = 29
            this[1] = 118
            this[2] = 48
            this[3] = 0
            this[4] = (width / 8).toByte()
            this[5] = 0
            this[6] = (length % 256).toByte()
            this[7] = (length / 256).toByte()
        }
    }

    fun toByteArray(): ByteArray {
        var s = 7
        for (i in 0 until length) {
            var k = 0
            while (k < width / 8) {
                val c0: Int = printBitmap.getPixel(k * 8 + 0, i)
                val p0: Byte = if (c0 == -1) 0 else 1

                val c1: Int = printBitmap.getPixel(k * 8 + 1, i)
                val p1: Byte = if (c1 == -1) 0 else 1

                val c2: Int = printBitmap.getPixel(k * 8 + 2, i)
                val p2: Byte = if (c2 == -1) 0 else 1

                val c3: Int = printBitmap.getPixel(k * 8 + 3, i)
                val p3: Byte = if (c3 == -1) 0 else 1

                val c4: Int = printBitmap.getPixel(k * 8 + 4, i)
                val p4: Byte = if (c4 == -1) 0 else 1

                val c5: Int = printBitmap.getPixel(k * 8 + 5, i)
                val p5: Byte = if (c5 == -1) 0 else 1

                val c6: Int = printBitmap.getPixel(k * 8 + 6, i)
                val p6: Byte = if (c6 == -1) 0 else 1

                val c7: Int = printBitmap.getPixel(k * 8 + 7, i)
                val p7: Byte = if (c7 == -1) 0 else 1

                val value = p0 * 128 + p1 * 64 + p2 * 32 + p3 * 16 + p4 * 8 + p5 * 4 + p6 * 2 + p7
                bitBuffer[k] = value.toByte()
                ++k
            }

            k = 0
            while (k < width / 8) {
                ++s
                imgBuffer[s] = bitBuffer[k]
                ++k
            }
        }

        return imgBuffer
    }
}