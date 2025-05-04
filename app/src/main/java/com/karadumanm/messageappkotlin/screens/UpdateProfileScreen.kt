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
import com.karadumanm.messageappkotlin.entities.AppUser
import com.karadumanm.messageappkotlin.ui.theme.secondaryColor
import com.karadumanm.messageappkotlin.viewmodel.UpdateProfileViewModel
import androidx.compose.runtime.getValue

@Composable
fun UpdateProfileScreen(viewModel : UpdateProfileViewModel, navController: NavHostController, appUser: AppUser){
    var email = remember { mutableStateOf("") }
    var displayName = remember { mutableStateOf("") }
    var password = remember {mutableStateOf("")}
    val context = LocalContext.current
    val updateMessage by viewModel.updateMessage.collectAsState()
    LaunchedEffect(updateMessage) {
        updateMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearUiMessage()
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(secondaryColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        EmailTextField(email = email.value, onValueChange = {email.value = it})

        Spacer(modifier = Modifier.padding(16.dp))

        DisplayNameTextField(displayName = displayName.value, onValueChange = {displayName.value = it})

        Spacer(modifier = Modifier.padding(16.dp))

        PasswordTextField(password = password.value, onValueChange = {password.value = it})

        Spacer(modifier = Modifier.padding(16.dp))

        UpdateButton(context= context, viewModel = viewModel, appUser = appUser, email = email.value, displayName = displayName.value, password = password.value)
    }
}

@Composable
fun UpdateButton(context: Context,viewModel: UpdateProfileViewModel, appUser: AppUser, email: String, displayName: String, password: String){
    Button(onClick = {viewModel.update(context, appUser, email, displayName, password)}) {
        Text("Update")
    }
}
