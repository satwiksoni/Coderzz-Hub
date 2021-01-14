package com.example.coderzz_assemble

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coderzz_assemble.Dao.PostDao
import com.example.coderzz_assemble.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IPostAdapter {
    lateinit var adaper:PostAdapetr
    lateinit var postDao:PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        postDao= PostDao()
        val postDaoCollection=postDao.PostCollection
        val query=postDaoCollection.orderBy("createdAt",Query.Direction.DESCENDING)
        val recyclerViewOptions=FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        adaper= PostAdapetr(recyclerViewOptions,this)
        rv.adapter=adaper
        rv.layoutManager=LinearLayoutManager(this)

    }

    fun fab(view: View) {
        val intent=Intent(this,CreatepostActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        adaper.startListening()
    }

    override fun onStop() {
        super.onStop()
        adaper.stopListening()
    }

    override fun onLickedListener(postId: String) {
        postDao.updateLikes(postId)


    }

}