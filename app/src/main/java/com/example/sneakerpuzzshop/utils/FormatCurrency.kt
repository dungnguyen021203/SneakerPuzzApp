package com.example.sneakerpuzzshop.utils

import java.text.NumberFormat
import java.util.Locale

fun formatCurrency(amount: String?): String {
    return try {
        val parsedAmount = amount?.toLongOrNull() ?: return "0 đ"
        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        formatter.format(parsedAmount)
    } catch (e: Exception) {
        "0 đ"
    }
}
