package com.example.coderzz_assemble.Dao

import com.example.coderzz_assemble.models.Post
import com.example.coderzz_assemble.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db=FirebaseFirestore.getInstance()
    val PostCollection=db.collection("posts")
    val auth=Firebase.auth
    fun addPost(text:String)
    {
        val currentUserId=auth.currentUser!!.uid
        GlobalScope.launch {

            val uDao=userDao()
            val user=uDao.getUserById(currentUserId).await().toObject(User::class.java)!!
            val currentTime=System.currentTimeMillis()
            val post=Post(text,user,currentTime)
            PostCollection.document().set(post)
        }
    }

    fun getPostById(postId: String): Task<DocumentSnapshot> {
        return PostCollection.document(postId).get()


    }

    fun updateLikes(postId:String)
    {
        GlobalScope.launch {

            val currentUserId=auth.currentUser!!.uid
            val post=getPostById(postId).await().toObject(Post::class.java)!!
            val isLiked= post.likedBy.contains(currentUserId)
            if(isLiked == true)
                post.likedBy.remove(currentUserId)
            else
                post.likedBy.add(currentUserId)
            PostCollection.document(postId).set(post)


        }


    }




}