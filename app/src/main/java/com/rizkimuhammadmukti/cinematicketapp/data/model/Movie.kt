package com.rizkimuhammadmukti.cinematicketapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val title: String,
    val genre: String,
    val duration: String,
    val rating: Float,
    val posterUrl: String,
    val synopsis: String,
    val showTimes: List<String>
) : Parcelable