package com.netomi.chat.utils

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.netomi.chat.R
import java.time.Instant
import java.time.ZoneId

object NCWDatePickerUtil {

    fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
        // Create a Material Date Picker
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select a date")
            .build()

        // Show the date picker
        datePicker.show((context as AppCompatActivity).supportFragmentManager, "DATE_PICKER")

        // Handle the selected date
        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).toLocalDate()
            val formattedDate = "${selectedDate.dayOfMonth}/${selectedDate.monthValue}/${selectedDate.year}"
            onDateSelected(formattedDate)
            Log.e("DatePickerUtil", "Selected date: $formattedDate")
        }
    }
}