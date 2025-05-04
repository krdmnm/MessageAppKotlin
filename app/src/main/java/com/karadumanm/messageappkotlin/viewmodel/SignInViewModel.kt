package com.karadumanm.messageappkotlin.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.karadumanm.messageappkotlin.repository.clearSharedPrefs
import com.karadumanm.messageappkotlin.repository.saveOnSharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow


class SignInViewModel:ViewModel() {

    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: MutableStateFlow<String?> = _uiMessage
    private val auth = Firebase.auth

    fun signIn(context: Context, email: String, password:String){
        println("SignInViewModel signIn: $email $password")
        if(email.isEmpty() || password.isEmpty()){
            _uiMessage.value = "Please enter your email and password"
        } else {
            _uiMessage.value = ""
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                println("SignInViewModel signIn(): Success")
                saveOnSharedPrefs(context, email, password)//From Repo.kt
            }.addOnFailureListener {
                println("SignInViewModel signIn(): Failed")
                Toast.makeText(context, "Sign In Failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signUp(navController: NavHostController){
        print("SignInViewModel signUp:")
        navController.navigate("SignUpScreen")
    }

    fun signOut(context: Context,navController: NavHostController){
        auth.signOut()
        clearSharedPrefs(context)//From Repo.kt
        navController.navigate("SignInScreen")
    }

    fun clearUiMessage(){
        _uiMessage.value = null
    }

}