package com.tbcacademy.shwopapp.ui.bottom_navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.adapters.PostAdapter
import com.tbcacademy.shwopapp.base.showDialog
import com.tbcacademy.shwopapp.base.snackbar
import com.tbcacademy.shwopapp.ui.bottom_navigation.sell_product.BasePostViewModel
import com.tbcacademy.shwopapp.utils.EventObserver
import javax.inject.Inject

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseHomeFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postAdapter.setOnLikeClickListener {post, i ->
            curLikedIndex = i
            post.isLiked = !post.isLiked
            baseViewModel.toggleLikeForPost(post)

        }

        postAdapter.setOnDeletePostClickListener {
            showDialog(R.string.are_you_sure_delete,"message")

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var postAdapter: PostAdapter

    protected abstract val postProgressBar: ProgressBar

    protected abstract val baseViewModel: BasePostViewModel

    private var curLikedIndex: Int? = null


    private fun observer() {
        baseViewModel.deletePostStatus.observe(viewLifecycleOwner,EventObserver(
            onError = {snackbar(it)}
        ){ deletedPost ->
            postAdapter.posts -= deletedPost

        })

        baseViewModel.likePostStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                curLikedIndex?.let { index ->
                    postAdapter.posts[index].isLiking = false
                    postAdapter.notifyItemChanged(index)
                }
                snackbar(it)

            },
            onLoading = {
                curLikedIndex?.let { index ->
                    postAdapter.posts[index].isLiking = true
                    postAdapter.notifyItemChanged(index)
                }

            }
        ){ isLiked ->
            curLikedIndex?.let {  index ->
                val uid = FirebaseAuth.getInstance().uid!!
                postAdapter.posts[index].apply {
                    this.isLiked = isLiked
                    if (isLiked) {
                        likedBy += uid
                    } else {
                        likedBy -= uid
                    }
                }
                postAdapter.notifyItemChanged(index)

            }

        })

        baseViewModel.posts.observe(viewLifecycleOwner, EventObserver(
            onError = {
                postProgressBar.isVisible = false
                snackbar(it)
            },
            onLoading = {
                postProgressBar.isVisible = true
            },
        ) { posts ->
            postProgressBar.isVisible = false
            postAdapter.posts = posts

        })

    }


}