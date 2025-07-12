package com.rizkimuhammadmukti.cinematicketapp.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "" // Note: This is just for structure, don't store actual passwords
)