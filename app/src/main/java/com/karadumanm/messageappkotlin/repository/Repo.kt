package com.karadumanm.messageappkotlin.repository

import android.content.Context
import androidx.navigation.NavHostController
import com.karadumanm.messageappkotlin.viewmodel.SignInViewModel


fun autoSignInOnStart(context: Context, viewModel: SignInViewModel, navController: NavHostController){
    val email = getFromSharedPrefs(context, "email", "")
    val password = getFromSharedPrefs(context, "password", "")
    if(email != "" && password != ""){
        println("autoSignInOnStart: $email $password")
        viewModel.signIn(context, navController, email, password)
    }
}

//SahredPreferences Jobs
fun saveOnSharedPrefs(context: Context, key: String, value: String){
    var sp = context.getSharedPreferences("MessageApp", Context.MODE_PRIVATE)
    val editor = sp.edit()
    editor.putString(key, value).apply()
}
fun getFromSharedPrefs(context: Context, key: String, defaultValue: String) : String {
    var sp = context.getSharedPreferences("MessageApp", Context.MODE_PRIVATE)
    val value = sp.getString(key, defaultValue)
    if(value != null){
        return value
    } else {
        return defaultValue
    }
}
fun clearSharedPrefs(context: Context){
    var sp = context.getSharedPreferences("MessageApp", Context.MODE_PRIVATE)
    sp.edit().clear().apply()
}