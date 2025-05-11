package com.karadumanm.messageappkotlin.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.karadumanm.messageappkotlin.database.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel:ViewModel() {

    private var _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: MutableStateFlow<String?> = _uiMessage
    private val auth = Firebase.auth
    private val db = Database()

    fun signUp(context: Context, navController: NavHostController, email: String, displayName: String, password: String){
        println("SignUpViewModel signUp: $email $displayName $password")
        viewModelScope.launch {
            val response =db.signUp(email,password,displayName)
            if(response){
                Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show()
                navController.navigate("SignInScreen")
            } else {
                Toast.makeText(context, "User creation failed, try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun goBack(navController: NavHostController){
        print("SignUpViewModel goBack:")
        navController.popBackStack("SignInScreen", false)
    }

    fun clearUiMessage(){
        _uiMessage.value = null
    }

}


/*
signUp
println("SignUpViewModel signUp: $email $displayName $password")
        var response = db.signUp(email, password, displayName)
        println("SignUpViewModel signUp(): response - $response")
        if(response){
            Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show()
            navController.navigate("SignInScreen")
        }
 */

/*
        //Register to firestore db
        val db = Firebase.firestore
        val user = hashMapOf("email" to email, "display_name" to displayName, "password" to password)
        db.collection("users").add(user).addOnSuccessListener {
            _uiMessage.value = "User created successfully"
            navController.navigate("SignInScreen")
            //Toast.makeText(context, "User created successfully", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            _uiMessage.value = "User creation failed, try again"
            //Toast.makeText(context, "User creation failed, try again", Toast.LENGTH_LONG).show()
        }
 */

/*signUp() Old version
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val user = Firebase.auth.currentUser
            var profileUpdates = userProfileChangeRequest {
                this.displayName = displayName
            }
            user?.updateProfile(profileUpdates)
            _uiMessage.value = "User created successfully"
            navController.navigate("SignInScreen")
        }.addOnFailureListener {
            _uiMessage.value = "User creation failed, try again"
        }
         */