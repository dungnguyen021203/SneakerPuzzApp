package com.example.sneakerpuzzshop.utils.others

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri

fun openEmailApp(context: Context, recipient: String, subject: String = "", body: String = "") {
    val uri = ("mailto:$recipient" +
            "?subject=" + Uri.encode(subject) +
            "&body=" + Uri.encode(body)).toUri()

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = uri
    }

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Lỗi khi mở ứng dụng email", Toast.LENGTH_SHORT).show()
    }
}