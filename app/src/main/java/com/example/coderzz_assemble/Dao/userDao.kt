package com.example.coderzz_assemble.Dao

import com.example.coderzz_assemble.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class userDao {

    private val db=FirebaseFirestore.getInstance()
    val userCollection=db.collection("user")

    fun addUser(user: User?)
    {
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                userCollection.document(user.uid).set(it)
            }
        }

    }

    fun getUserById(uId:String): Task<DocumentSnapshot>
    {
        return userCollection.document(uId).get()

    }

}