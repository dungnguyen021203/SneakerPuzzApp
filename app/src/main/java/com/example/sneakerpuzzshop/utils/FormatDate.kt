package com.example.sneakerpuzzshop.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatTimestamp(timestamp: com.google.firebase.Timestamp): String {
    val date = timestamp.toDate()
    val formatter = SimpleDateFormat("dd/MM/yyyy h:mma", Locale.getDefault())
    return formatter.format(date)
}