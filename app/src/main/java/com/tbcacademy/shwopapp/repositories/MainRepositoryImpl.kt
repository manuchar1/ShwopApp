package com.tbcacademy.shwopapp.repositories


import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tbcacademy.shwopapp.base.safeCall
import com.tbcacademy.shwopapp.data.Post
import com.tbcacademy.shwopapp.data.User
import com.tbcacademy.shwopapp.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException
import java.util.*

@ActivityScoped
class MainRepositoryImpl {

    private val auth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private val storage = Firebase.storage
    private val users = fireStore.collection("users")
    private val posts = fireStore.collection("posts")
    private val comments = fireStore.collection("comments")


    suspend fun createPost(imageUri: Uri,
                           secondImageUrl: Uri,
                           thirdImageUrl: Uri,
                           fourImageUrl: Uri,
                           product: String,
                           text: String,
                           price: String) =
        withContext(Dispatchers.IO) {
            safeCall {
                val uid = auth.uid!!
                val postId = UUID.randomUUID().toString()
                val imageUploadResult = storage.getReference(postId).putFile(imageUri).await()
                val imageUrl1 = imageUploadResult?.metadata?.reference?.downloadUrl?.await().toString()

                val imageUploadResult2 = storage.getReference(postId).putFile(secondImageUrl).await()
                val imageUrl2 = imageUploadResult2?.metadata?.reference?.downloadUrl?.await().toString()

                val imageUploadResult3 = storage.getReference(postId).putFile(thirdImageUrl).await()
                val imageUrl3 = imageUploadResult3?.metadata?.reference?.downloadUrl?.await().toString()

                val imageUploadResult4 = storage.getReference(postId).putFile(fourImageUrl).await()
                val imageUrl4 = imageUploadResult4?.metadata?.reference?.downloadUrl?.await().toString()

                val post = Post(
                    id = postId,
                    authorUid = uid,
                    text = text,
                    price = price,
                    imageUrl = imageUrl1,
                    secondImageUrl = imageUrl2,
                    thirdImageUrl = imageUrl3,
                    fourImageUrl = imageUrl4,
                    product = product,
                    date = System.currentTimeMillis()
                )
                posts.document(postId).set(post).await()
                Resource.Success(Any())


            }

        }

    suspend fun deletePost(post: Post) = withContext(Dispatchers.IO) {
        safeCall {
            posts.document(post.id).delete().await()
            storage.getReferenceFromUrl(post.imageUrl).delete().await()
            Resource.Success(post)
        }
    }

    suspend fun toggleLikeForPost(post: Post) = withContext(Dispatchers.IO) {
        safeCall {
            var isLiked = false
            fireStore.runTransaction { transaction ->
                val uid = FirebaseAuth.getInstance().uid!!
                val postResult = transaction.get(posts.document(post.id))
                val currentLikes = postResult.toObject(Post::class.java)?.likedBy ?: listOf()
                transaction.update(
                    posts.document(post.id),
                    "likedBy",
                    if (uid in currentLikes) currentLikes - uid else {
                        isLiked = true
                        currentLikes + uid
                    }
                )
            }.await()
            Resource.Success(isLiked)
        }
    }


    suspend fun getUsers(uids: List<String>) = withContext(Dispatchers.IO) {
        safeCall {
            val chunks = uids.chunked(10)
            val resultList = mutableListOf<User>()
            chunks.forEach { chunk ->
                val usersList = users.whereIn("uid", uids).orderBy("username").get().await()
                    .toObjects(User::class.java)
                resultList.addAll(usersList)
            }
            Resource.Success(resultList.toList())
        }
    }


    suspend fun getUser(uid: String) = withContext(Dispatchers.IO) {
        safeCall {
            val user = users.document(uid).get().await().toObject(User::class.java)
                ?: throw IllegalStateException()
            val currentUid = FirebaseAuth.getInstance().uid!!
            val currentUser = users.document(currentUid).get().await().toObject(User::class.java)
                ?: throw IllegalStateException()
            user.isFollowing = uid in currentUser.follows
            Resource.Success(user)
        }
    }

    suspend fun getPostsForFollows() = withContext(Dispatchers.IO) {
        safeCall {
            val uid = FirebaseAuth.getInstance().uid!!
            val follows = getUser(uid).data!!.follows
            val allPosts = posts.whereIn("authorUid", follows)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
                .toObjects(Post::class.java)
                .onEach { post ->

                    val user = getUser(post.authorUid).data!!
                    post.authorProfilePictureUrl = user.profilePicture
                    post.isLiked = uid in post.likedBy

                }
            Resource.Success(allPosts)
        }


    }

}