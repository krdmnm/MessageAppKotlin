package com.karadumanm.messageappkotlin.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.karadumanm.messageappkotlin.database.Database
import com.karadumanm.messageappkotlin.repository.clearSharedPrefs
import com.karadumanm.messageappkotlin.repository.saveOnSharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class SignInViewModel:ViewModel() {

    private val db = Database()
    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: MutableStateFlow<String?> = _uiMessage
    private val auth = Firebase.auth

    fun signIn(context: Context, navController: NavHostController,email: String, password:String){
        viewModelScope.launch{
            val response = db.signIn(email, password)
            if(response){
                _uiMessage.value = "Signed In Successfully"
                saveOnSharedPrefs(context, email, password)
                navController.navigate("MessagesScreen")
            } else {
                _uiMessage.value = "Sign In Failed, Try Again"
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