package com.karadumanm.messageappkotlin.database

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.firestore

class Database {
    private val auth = Firebase.auth
    private val fireStore = Firebase.firestore

    fun signUp(email: String, password: String, displayName: String) : Boolean{
        var isSuccessful : Boolean = false
        println("Database signUp(): $email $password")
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            println("Database signUp(): task.isSuccessful: ${task.isSuccessful}, isCanceled ${task.isCanceled}, " +
                    "isComplete ${task.isComplete}, Result: ${task.result}, User: ${task.result?.user?.uid}")
            if(task.isSuccessful){
                val user = Firebase.auth.currentUser
                var profileUpdates = userProfileChangeRequest {
                    this.displayName = displayName
                }
                user?.updateProfile(profileUpdates)
                val emptyDoc = hashMapOf<String, String>()
                fireStore.collection(task.result.user!!.uid).document("init").set(emptyDoc).addOnSuccessListener {
                    println("Database signUp(): firestore collection created successfully")
                    isSuccessful = true
                }.addOnFailureListener {
                    println("Database signUp(): firestore collection creation failed")
                    isSuccessful = false
                }
            } else {
                isSuccessful = false
            }
        }
        return isSuccessful
    }

}