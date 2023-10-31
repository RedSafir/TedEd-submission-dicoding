package com.miftah.mysubmissionintermediate.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Base64
import java.io.File
import java.io.FileOutputStream

fun String.base64ToUri(context: Context): Uri? {
    try {
        // Decode the Base64 string to obtain a byte array
        val decodedBytes = Base64.decode(this, Base64.DEFAULT)

        // Create a Bitmap from the byte array
        val bitmap = Bitmap.createBitmap(BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size))

        // Create a file to save the Bitmap
        val directory = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_images")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, "image.jpg")

        // Save the Bitmap to the file
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()

        // Get the URI of the saved file
        return Uri.fromFile(file)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}
