/*
package com.tbcacademy.shwopapp.repositories

import android.net.Uri
import com.tbcacademy.shwopapp.data.Post
import com.tbcacademy.shwopapp.data.User
import com.tbcacademy.shwopapp.utils.Resource

interface MainRepository {

    suspend fun createPost(imageUri: Uri, text: String, price: String): Resource<Any>
    suspend fun getUsers(uids: List<String>): Resource<List<User>>
    suspend fun getUser(uid: String): Resource<User>
    suspend fun getPostsForFollows(): Resource<List<Post>>
    suspend fun toggleLikeForPost(post: Post): Resource<Boolean>
    suspend fun deletePost(post: Post): Resource<Post>
}*/
