package com.example.mobileapp_assignmentv1.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fixtures(var idClub: Long = 0,
                     var team1: String = "",
                     var team2: String = "",
                     var score1: Int = 0,
                     var score2: Int = 0,
                     var venue:  String = "",
                     var date: String = ""
) : Parcelable