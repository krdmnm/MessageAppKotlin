package com.karadumanm.messageappkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.karadumanm.messageappkotlin.entities.AppUser
import com.karadumanm.messageappkotlin.repository.getFromSharedPrefs
import com.karadumanm.messageappkotlin.screens.AppBar
import com.karadumanm.messageappkotlin.screens.MessagesScreen
import com.karadumanm.messageappkotlin.screens.SignInScreen
import com.karadumanm.messageappkotlin.screens.SignUpScreen
import com.karadumanm.messageappkotlin.screens.UpdateProfileScreen
import com.karadumanm.messageappkotlin.ui.theme.MessageAppKotlinTheme
import com.karadumanm.messageappkotlin.viewmodel.MessagesViewModel
import com.karadumanm.messageappkotlin.viewmodel.SignInViewModel
import com.karadumanm.messageappkotlin.viewmodel.SignUpViewModel
import com.karadumanm.messageappkotlin.viewmodel.UpdateProfileViewModel

class MainActivity : ComponentActivity() {
    private val signInViewModel : SignInViewModel by viewModels<SignInViewModel>()
    private val signUpViewModel : SignUpViewModel by viewModels<SignUpViewModel>()
    private val updateProfileViewModel : UpdateProfileViewModel by viewModels<UpdateProfileViewModel>()
    private val messagesViewModel : MessagesViewModel by viewModels<MessagesViewModel>()
    private lateinit var appUser : AppUser
    private var messages : List<String> = listOf("Message 1", "Message 2", "Message 3",
        "Message 1", "Message 2", "Message 3",
        "Message 1", "Message 2", "Message 3",
        "Message 1", "Message 2", "Message 3",
        "Message 1", "Message 2", "Message 3")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MessageAppKotlinTheme {
                Scaffold(topBar = { AppBar(signInViewModel, navController) },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        NavHost(navController, startDestination = "SignInScreen"){
                            composable(route = "SignInScreen"){
                                SignInScreen(viewModel = signInViewModel, navController = navController)
                            }
                            composable(route = "SignUpScreen"){
                                SignUpScreen(viewModel = signUpViewModel, navController = navController)
                            }
                            composable (route= "UpdateProfileScreen") {
                                UpdateProfileScreen(viewModel = updateProfileViewModel, appUser = appUser, navController = navController)
                            }
                            composable (route = "MessagesScreen") {
                                MessagesScreen(viewModel = messagesViewModel, navController= navController, messages = messages)
                            }

                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val email = getFromSharedPrefs(this, "email", "")//From Repo.kt
        val password = getFromSharedPrefs(this, "password", "")//From Repo.kt
        println("MainActivity onStart(): $email $password")
        if(email != "" && password != ""){
            signInViewModel.signIn(this, email, password)
            //Şimdi currentUser alıp AppUser objesi yapalım ve Message sayfasına navigate edelim.
            val auth = Firebase.auth
            val currentUser = auth.currentUser
            if(currentUser != null){
                appUser = AppUser(currentUser.uid, currentUser.email!!, currentUser.displayName!!)
                //messages sayfasına navigate edelim.
            }
        }
    }
}


/*


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MessageAppKotlinTheme {
        SignInScreen()
    }
}
*/