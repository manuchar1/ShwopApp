package com.tbcacademy.shwopapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.data.Post
import com.tbcacademy.shwopapp.databinding.ItemPostBinding
import javax.inject.Inject

class PostAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = posts.size


    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val post = posts[adapterPosition]
            itemView.apply {
                glide.load(post.imageUrl).into(binding.ivImage)
                glide.load(post.authorProfilePictureUrl).into(binding.ivPostAuthor)
                binding.tvAuthorName.text = post.authorUsername
                binding.tvProductType.text = post.product
                binding.tvPrice.text = "₾ ${post.price}"
                binding.tvDetails.text = post.text
                val likeCounter = post.likedBy.size
                binding.tvLiked.text = when {
                    likeCounter <= 0 -> "No likes"
                    else -> "$likeCounter"
                }
                val uid = FirebaseAuth.getInstance().uid!!
                binding.btnDeleteOwnPost.isVisible = uid == post.authorUid
                binding.btnLike.setImageResource(
                    if (post.isLiked) {
                        R.drawable.ic_bold_heart
                    }else{
                        R.drawable.ic_heart
                    }
                )
                binding.apply {
                    tvAuthorName.setOnClickListener {
                        onUserClickListener?.let { click ->
                            click(post.authorUid)

                        }
                    }
                    ivPostAuthor.setOnClickListener {
                        onUserClickListener?.let { click ->
                            click(post.authorUid)

                        }
                    }
                    tvLiked.setOnClickListener {
                        onLikedByClickListener?.let { click ->
                            click(post)

                        }
                    }
                    btnLike.setOnClickListener {
                        onLikeClickListener?.let { click ->
                            if (!post.isLiking)
                            click(post, layoutPosition)

                        }
                    }
                    btnComments.setOnClickListener {
                        onCommentsClickListener?.let { click ->
                            click(post)

                        }
                    }
                    btnDeleteOwnPost.setOnClickListener {
                        onDeletePostClickListener?.let { click ->
                            click(post)

                        }
                    }

                }

            }


        }
    }

    private var onLikeClickListener: ((Post, Int) -> Unit)? = null
    private var onUserClickListener: ((String) -> Unit)? = null
    private var onDeletePostClickListener: ((Post) -> Unit)? = null
    private var onLikedByClickListener: ((Post) -> Unit)? = null
    private var onCommentsClickListener: ((Post) -> Unit)? = null


    fun setOnLikeClickListener(listener: (Post, Int) -> Unit) {
        onLikeClickListener = listener
    }

    fun setOnUserClickListener(listener: (String) -> Unit) {
        onUserClickListener = listener
    }

    fun setOnDeletePostClickListener(listener: (Post) -> Unit) {
        onDeletePostClickListener = listener
    }

    fun setOnLikedByClickListener(listener: (Post) -> Unit) {
        onLikedByClickListener = listener
    }

    fun setOnCommentsClickListener(listener: (Post) -> Unit) {
        onCommentsClickListener = listener
    }


    private val differCallBack = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private val differ = AsyncListDiffer(this, differCallBack)

    var posts: List<Post>
        get() = differ.currentList
        set(value) = differ.submitList(value)




}