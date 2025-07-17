package com.example.sneakerpuzzshop.utils.others

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun formatTimestamp(timestamp: Timestamp): String {
    val date = timestamp.toDate()
    val formatter = SimpleDateFormat("dd/MM/yyyy h:mma", Locale.getDefault())
    return formatter.format(date)
}