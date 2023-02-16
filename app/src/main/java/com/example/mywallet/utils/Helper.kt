package com.example.mywallet.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Helper {
    fun toRupiah(p0: Int): String {
        val locale = Locale("in", "ID")
        return NumberFormat.getCurrencyInstance(locale).format(p0).substringBefore(",")
    }

    fun String.currency(): String {
        val localeID = Locale("in", "ID")
        val doubleValue = this.toDoubleOrNull() ?: return this
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.minimumFractionDigits = 0
        return numberFormat.format(doubleValue)
    }

    fun formattingDate(date: String?): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val inputDate = format.parse(date.toString())
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("in", "ID"))
        return dateFormat.format(inputDate as Date)
    }

}