package com.example.sneakerpuzzshop.utils

import java.text.NumberFormat
import java.util.Locale

fun formatCurrency(amount: String): String {
    val amount = amount.toLong()
    val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
    return formatter.format(amount)
}
