package com.tbcacademy.shwopapp.data


import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
data class User(
    val uid: String = "",
    val name: String = "",
    val profilePicture: String = "",
    val description: String = "",
    var follows: List<String> = listOf(),
    @get: Exclude
    var isFollowing: Boolean = false


)


