package com.example.coderzz_assemble

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coderzz_assemble.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostAdapetr(options: FirestoreRecyclerOptions<Post>,val listener:IPostAdapter) : FirestoreRecyclerAdapter<Post, PostAdapetr.PostViewHolder>(options) {
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val postText:TextView=itemView.findViewById(R.id.postTitle)
        val userText:TextView=itemView.findViewById(R.id.userName)
        val createdAt:TextView=itemView.findViewById(R.id.createdAt)
        val likeCount:TextView=itemView.findViewById(R.id.likeCount)
        val userImage:ImageView=itemView.findViewById(R.id.userImage)
        val likeButton:ImageView=itemView.findViewById(R.id.likeButton)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val pvh = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
        pvh.likeButton.setOnClickListener {
            listener.onLickedListener(snapshots.getSnapshot(pvh.adapterPosition).id)
        }
    return pvh
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.createdAt.text= Utils.getTimeAgo(model.createdAt)
        holder.postText.text=model.text
        holder.userText.text=model.createdBy.displayName.toString()
        Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.likeCount.text=model.likedBy.size.toString()
        val auth= Firebase.auth
        val currentUser=auth.currentUser!!.uid
        val isLiked=model.likedBy.contains(currentUser)
        if(isLiked)
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_licked))
        else
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_unliked))



    }
}

interface IPostAdapter
{
    fun onLickedListener(postId:String)
}