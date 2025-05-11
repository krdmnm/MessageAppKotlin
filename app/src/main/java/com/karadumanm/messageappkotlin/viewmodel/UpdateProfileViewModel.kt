package com.karadumanm.messageappkotlin.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.karadumanm.messageappkotlin.database.Database
import com.karadumanm.messageappkotlin.entities.AppUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UpdateProfileViewModel: ViewModel() {

    private val auth = Firebase.auth
    private val currentUser = auth.currentUser
    private val db = Database()
    private var messageSuccess = ""
    private var messageFailure = ""
    var updateMessage = MutableStateFlow<String?>(null)


    fun update(context: Context, appUser: AppUser,email: String, displayName: String, password: String){

        //Update email
        if(email != appUser.email){
            viewModelScope.launch {
                val response = db.updateEmail(currentUser, email)
                if(response){
                    messageSuccess = messageSuccess + "Email "
                } else {
                    messageFailure = messageFailure + "Email "
                }
            }
        }

        //Update display name
        if(displayName != appUser.displayName){
            viewModelScope.launch {
                val response = db.updateDisplayName(currentUser, displayName)
                if(response){
                    messageSuccess = messageSuccess + "Display Name "
                } else {
                    messageFailure = messageFailure + "Display Name "
                }
            }
        }

        //Update password
        if(password != ""){
            viewModelScope.launch {
                val response = db.updatePassword(currentUser, password)
                if(response){
                    messageSuccess = messageSuccess + "Password "
                } else {
                    messageFailure = messageFailure + "Password "
                }
            }
        }

        if(messageSuccess != ""){
            updateMessage.value = messageSuccess + "updated. "
        }
        if(messageFailure != ""){
            updateMessage.value = messageFailure + "not updated."
        }
        println("UpdateProfileViewModel.update() updateMessage: ${updateMessage.value}")
    }

    fun clearUiMessage(){
        updateMessage.value = null
    }

}

/*

currentUser?.let {
            if(email != appUser.email){
                //Update email
                currentUser.updateEmail(email).addOnSuccessListener {
                    messageSuccess = messageSuccess + "Email "
                }.addOnFailureListener {
                    messageFailure = messageFailure + "Email "
                }
            }
            if(displayName != appUser.displayName){
                //Update displayName
                val profileUpdates = userProfileChangeRequest {
                    this.displayName = displayName
                }
                currentUser.updateProfile(profileUpdates).addOnSuccessListener {
                    messageSuccess = messageSuccess + "Display Name "
                }.addOnFailureListener {
                    messageFailure = messageFailure + "Display Name "
                }

            }
            if(password != ""){
                currentUser.updatePassword(password).addOnSuccessListener {
                    messageSuccess = messageSuccess + "Password "
                }.addOnFailureListener {
                    messageFailure = messageFailure + "Password "
                }
            }
        }

 */