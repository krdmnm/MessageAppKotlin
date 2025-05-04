package com.karadumanm.messageappkotlin.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.karadumanm.messageappkotlin.ui.theme.designColor
import com.karadumanm.messageappkotlin.ui.theme.goBackArrow
import com.karadumanm.messageappkotlin.ui.theme.secondaryColor
import com.karadumanm.messageappkotlin.viewmodel.SignUpViewModel


@Composable
fun SignUpScreen(viewModel: SignUpViewModel, navController: NavHostController){

    var email = remember { mutableStateOf("") }
    var displayName = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    val context = LocalContext.current
    val uiMessage by viewModel.uiMessage.collectAsState()
    LaunchedEffect(uiMessage) {
        uiMessage?.let{
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearUiMessage()
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(secondaryColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        EmailTextField(email.value, onValueChange = {email.value = it})

        Spacer(modifier = Modifier.padding(16.dp))

        DisplayNameTextField(displayName.value, onValueChange = {displayName.value = it})

        Spacer(modifier = Modifier.padding(16.dp))

        PasswordTextField(password = password.value, onValueChange = {password.value = it})

        Spacer(modifier = Modifier.padding(16.dp))

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            SignUpButton(context = context, navController = navController, viewModel = viewModel, email = email.value, displayName = displayName.value, password = password.value)
            Spacer(modifier = Modifier.padding(16.dp))
            GoBackButton(viewModel = viewModel, navController = navController)
        }

    }

}

@Composable
private fun SignUpButton(context: Context, navController: NavHostController, viewModel: SignUpViewModel, email: String, displayName: String, password: String){
    Button(onClick = {viewModel.signUp (context, navController, email, displayName, password)}) {
        Text("Sign Up")
    }
}

@Composable
private fun GoBackButton(viewModel : SignUpViewModel, navController: NavHostController){
    Button(onClick = {viewModel.goBack(navController)}) {
        IconButton(onClick = {viewModel.goBack(navController)}) {
            Icon(painter = painterResource(id = goBackArrow), contentDescription = "Go Back", tint = designColor)
        }
    }
}