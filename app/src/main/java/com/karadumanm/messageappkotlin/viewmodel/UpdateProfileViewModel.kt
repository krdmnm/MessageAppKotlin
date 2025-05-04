package com.karadumanm.messageappkotlin.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.karadumanm.messageappkotlin.entities.AppUser
import kotlinx.coroutines.flow.MutableStateFlow

class UpdateProfileViewModel: ViewModel() {

    private val auth = Firebase.auth
    private val currentUser = auth.currentUser
    private var messageSuccess = ""
    private var messageFailure = ""
    var updateMessage = MutableStateFlow<String?>(null)


    fun update(context: Context, appUser: AppUser,email: String, displayName: String, password: String){
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