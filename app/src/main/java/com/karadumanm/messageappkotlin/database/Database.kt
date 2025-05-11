package com.karadumanm.messageappkotlin.database

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class Database {
    private val auth = Firebase.auth
    private val fireStore = Firebase.firestore

    suspend fun signUp(email: String, password: String, displayName: String) : Boolean{
        println("Database signUp(): $email $password")
        return try {
            //register user to auth
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: return false
            //Update user
            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
            user.updateProfile(profileUpdates).await()
            //Create user collection with uid and persons document
            val emptyDoc = hashMapOf<String, String>()
            fireStore.collection(user.uid).document("persons").set(emptyDoc).await()
            true
        } catch (e: Exception){
            println("Database signUp(): Exception: ${e.message}")
            false
        }
    }

    suspend fun updateEmail(currentUser: FirebaseUser?, email: String) : Boolean{
        var isSuccessful = false
        return try {
            currentUser?.updateEmail(email)?.addOnCompleteListener { task ->
               if(task.isSuccessful){
                   println("Database updateEmail(): Email updated")
                   isSuccessful = true
               } else {
                   println("Database updateEmail(): Email not updated")
                   isSuccessful = false
               }
            }?.await()
            isSuccessful
        } catch (e: Exception){
            println("Database signUp(): Exception: ${e.message}")
            false
        }
    }

    suspend fun updateDisplayName(currentUser: FirebaseUser?, displayName: String): Boolean{
        var isSuccessful = false
        return try {
            val userProfileUpdates = UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
            currentUser?.updateProfile(userProfileUpdates)?.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    println("Database updateDisplayName(): Display Name updated")
                    isSuccessful = true
                } else {
                    println("Database updateDisplayName(): Display Name not updated")
                    isSuccessful = false
                }
            }?.await()
            isSuccessful
        }catch (e: Exception){
            println("Database signUp(): Exception: ${e.message}")
            false
        }
    }

    suspend fun updatePassword(currentUser: FirebaseUser?, password: String): Boolean{
        var isSuccessful = false
        return try {
            currentUser?.updatePassword(password)?.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    println("Database updatePassword(): Password updated")
                    isSuccessful = true
                } else {
                    println("Database updatePassword(): Password not updated")
                    isSuccessful = false
                }
            }?.await()
           isSuccessful
        } catch (e: Exception){
            println("Database signUp(): Exception: ${e.message}")
            false
        }
    }

}

/*Without coroutine and with completeListener
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

