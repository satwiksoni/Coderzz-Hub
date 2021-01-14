package com.example.coderzz_assemble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coderzz_assemble.Dao.PostDao
import kotlinx.android.synthetic.main.activity_createpost.*

class CreatepostActivity : AppCompatActivity() {
    lateinit var postDao:PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createpost)
        bt1.setOnClickListener{
            val text=et1.editableText.toString().trim()
            if(text.isNotEmpty())
            {
                postDao= PostDao()
                postDao.addPost(text)
                finish()

            }

        }
    }
}