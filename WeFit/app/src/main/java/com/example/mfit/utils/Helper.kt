package com.example.mfit.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    companion object{
        fun getDisplayDate(): String{
            val date: Date = Calendar.getInstance().time
            val df: DateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            return df.format(date)
        }

        fun getCalories(steps: Int): String {
            val cal = (steps * 0.045).toInt()
            return "$cal calories"
        }


        fun getDistanceCovered(steps: Int): String {
            val feet = (steps * 2.5).toInt()
            val distance = feet/3.281
            val finalDistance:Double = String.format("%.2f", distance).toDouble()
            return "$finalDistance meter"
        }

        fun getCurrentDate(): String{
            val date: Date = Calendar.getInstance().time
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            return df.format(date)
        }
    }
}