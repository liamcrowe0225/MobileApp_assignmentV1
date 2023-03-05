package com.example.mobileapp_assignmentv1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Fixtures(var idClub: Long = 0,
                     var team1: String = "",
                     var team2: String = "",
                     var score1: Int = 0,
                     var score2: Int = 0,
                     var venue:  String = "",
                     var date: String = ""
) : Parcelable /*{
    fun getFormattedDate(): String {
        val dateObj = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
        return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(dateObj)
    }
}
*/