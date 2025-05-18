package com.karadumanm.messageappkotlin.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.karadumanm.messageappkotlin.ui.theme.designColor
import com.karadumanm.messageappkotlin.ui.theme.primaryColor
import com.karadumanm.messageappkotlin.ui.theme.secondaryColor
import com.karadumanm.messageappkotlin.viewmodel.SignInViewModel
import androidx.compose.runtime.getValue
import com.karadumanm.messageappkotlin.repository.autoSignInOnStart


@Composable
fun SignInScreen(viewModel: SignInViewModel, navController: NavHostController){
    val context = LocalContext.current
    //autoSignInOnStart(context, viewModel, navController)
    var email  = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    val uiMessage by viewModel.uiMessage.collectAsState()
    LaunchedEffect(uiMessage) {
        uiMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearUiMessage()
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(secondaryColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailTextField(email.value, onValueChange = ({email.value = it}))

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(password.value, onValueChange = ({password.value = it}))

        Spacer(modifier = Modifier.padding(16.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            SignInButton(context, viewModel, navController, email.value, password.value)
            SignUpButton(viewModel, navController)
        }
    }
}

@Composable
fun SignInButton(context: Context, viewModel: SignInViewModel, navController: NavHostController, email: String, password: String){
    Button(onClick = {viewModel.signIn(context, navController, email, password)},
        colors = ButtonDefaults.buttonColors(contentColor = primaryColor, containerColor = designColor)
        )
    {
        Text("Sign In")
    }
}

@Composable
fun SignUpButton(viewModel: SignInViewModel, navController: NavHostController){
    Button(onClick = {viewModel.signUp(navController)},
        colors = ButtonDefaults.buttonColors(contentColor = primaryColor, containerColor = designColor)
    )
    {
        Text("Sign Up")
    }
}