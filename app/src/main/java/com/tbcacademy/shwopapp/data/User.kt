package com.tbcacademy.shwopapp.data


import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
data class User(
    val uid: String = "",
    val name: String = "",
   // val profilePicture: String = DEFAULT_PROFILE_PICTURE_URL,
    val description: String = "",
/*    var follows: List<String> = listOf(),
    @Exclude
    var isFollowing: Boolean = false*/


)


