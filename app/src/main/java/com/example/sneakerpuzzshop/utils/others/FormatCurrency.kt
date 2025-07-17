package com.example.sneakerpuzzshop.utils.others

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

fun formatCurrency(amount: Double?): String {
    return try {
        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        formatter.format(amount ?: 0.0)
    } catch (e: Exception) {
        "0 đ"
    }
}
