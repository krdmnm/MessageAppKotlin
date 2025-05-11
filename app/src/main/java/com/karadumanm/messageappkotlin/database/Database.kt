package com.karadumanm.messageappkotlin.database

import com.google.firebase.Firebase
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class Database {
    private val auth = Firebase.auth
    private val fireStore = Firebase.firestore

    suspend fun signUp(email: String, password: String, displayName: String) : Boolean{
        var isSuccessful : Boolean = false
        println("Database signUp(): $email $password")
        //Register user to auth
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: return false
            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
            user.updateProfile(profileUpdates).await()
            val emptyDoc = hashMapOf<String, String>()
            fireStore.collection(user.uid).document("persons").set(emptyDoc).await()
            true
        } catch (e: Exception){
            println("Database signUp(): Exception: ${e.message}")
            false
        }
    }
}



/*
auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { response ->
            println("Database signUp(): User registered to Auth - response : $response")
            //Update user display name after registering
            val user = Firebase.auth.currentUser
            val profileUpdates = userProfileChangeRequest {
                this.displayName = displayName
            }
            user?.updateProfile(profileUpdates)
            //Create user collection with uid and persons document
            val emptyDoc = hashMapOf<String, String>()
            fireStore.collection(user!!.uid).document("persons").set(emptyDoc).addOnSuccessListener {
                println("Database signUp(): firestore collection created successfully")
                isSuccessful = true
            }.addOnFailureListener {
                println("Database signUp(): firestore collection creation failed")
                isSuccessful = false
            }
        }
        return isSuccessful
 */

/*
.addOnCompleteListener { task ->
            println("Database signUp(): task.isSuccessful: ${task.isSuccessful}, isCanceled ${task.isCanceled}, " +
                    "isComplete ${task.isComplete}, Result: ${task.result}, User: ${task.result?.user?.uid}")
            if(task.isSuccessful){
                val user = Firebase.auth.currentUser
                var profileUpdates = userProfileChangeRequest {
                    this.displayName = displayName
                }
                user?.updateProfile(profileUpdates)
                val emptyDoc = hashMapOf<String, String>()
                fireStore.collection(task.result.user!!.uid).document("persons").set(emptyDoc).addOnSuccessListener {
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
 */