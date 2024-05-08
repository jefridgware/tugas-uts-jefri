package com.example.myappgit.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class userResponese(
    var username: String,
    var name: String,
    var avatar: Int,
    var company: String,
    var location: String,
    var repository: Int,
    var followers: Int,
    var following: Int
) : Parcelable


