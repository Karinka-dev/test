package com.shakuro.test

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var login: String,
    var id: Int,
    var avatar_url: String
) : Parcelable