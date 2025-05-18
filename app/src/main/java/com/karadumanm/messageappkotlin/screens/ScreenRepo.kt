package com.karadumanm.messageappkotlin.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.karadumanm.messageappkotlin.entities.AppUser
import com.karadumanm.messageappkotlin.ui.theme.designColor
import com.karadumanm.messageappkotlin.ui.theme.visibilityIcon
import com.karadumanm.messageappkotlin.ui.theme.visibilityOffIcon
import com.karadumanm.messageappkotlin.viewmodel.SignInViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

//Appbar MainActivity uses
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(viewModel: SignInViewModel, navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    //val currentRoute = navController.currentBackStackEntry?.destination?.route
    val currentRoute = navBackStackEntry?.destination?.route
    if(currentRoute == "MessagesScreen"){
        TopAppBar(title = { Text("Messages") },
            actions = {
                OptionsMenu(viewModel, navController)
            })
    } else {
        TopAppBar(title = { Text("Message App") })
    }
}

@Composable
fun OptionsMenu(viewModel: SignInViewModel, navController: NavHostController){
    var expanded = remember { mutableStateOf(false) }
    val context: Context = LocalContext.current

    Box(){
        IconButton(onClick = {expanded.value = true}) {
            Icon(Icons.Default.MoreVert, contentDescription = "Options Menu")
        }
    }

    DropdownMenu(expanded = expanded.value,
        onDismissRequest = {expanded.value = false}) {

        DropdownMenuItem(text = {Text("Update Profile")},
            onClick = {expanded.value = false
            navController.navigate("UpdateProfileScreen")
            })

        DropdownMenuItem(text = {Text("Add Your Friend")},
            onClick = {expanded.value = false
            navController.navigate("AddPersonScreen")
            })

        DropdownMenuItem(text = {Text("Sign Out")},
            onClick = {expanded.value = false
            viewModel.signOut(context, navController)
            })
    }

}

//TextFields - SignInScreen, SignUpScreen, UpdateProfileScreen uses
@Composable
fun EmailTextField(email: String, onValueChange: (String)->Unit){
    OutlinedTextField(
        value = email,
        onValueChange = {onValueChange(it)},
        label = {Text("E-mail")},
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = designColor, unfocusedTextColor = designColor,
            focusedBorderColor = designColor, unfocusedBorderColor = designColor,
            focusedLabelColor = designColor, unfocusedLabelColor = designColor)
    )
}

@Composable
fun DisplayNameTextField(displayName: String, onValueChange:(String)->Unit){
    OutlinedTextField(
        value = displayName,
        onValueChange = {onValueChange(it)},
        label = { Text("Display Name") },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = designColor, unfocusedTextColor = designColor,
            focusedBorderColor = designColor, unfocusedBorderColor = designColor,
            focusedLabelColor = designColor, unfocusedLabelColor = designColor)
    )
}

@Composable
fun PasswordTextField(password: String, onValueChange: (String)->Unit){
    var passwordVisibility = remember {mutableStateOf(false)}
    OutlinedTextField(
        value = password,
        onValueChange = {onValueChange(it)},
        label = { Text("Password") },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = designColor, unfocusedTextColor = designColor,
            focusedBorderColor = designColor, unfocusedBorderColor = designColor,
            focusedLabelColor = designColor, unfocusedLabelColor = designColor),
        visualTransformation = if(passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if(passwordVisibility.value) painterResource(id = visibilityOffIcon) else painterResource(id = visibilityIcon)
            IconButton(onClick = {passwordVisibility.value = !passwordVisibility.value}) {
                Icon(painter = image, contentDescription = "Toggle password visibility", tint = designColor)
            }
        }
    )
}