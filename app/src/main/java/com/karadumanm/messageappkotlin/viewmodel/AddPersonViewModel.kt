package com.karadumanm.messageappkotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.karadumanm.messageappkotlin.database.Database
import com.karadumanm.messageappkotlin.entities.Person
import kotlinx.coroutines.launch

class AddPersonViewModel: ViewModel() {

    private val db = Database()
    private val listOfPersons = mutableListOf<Person>()

    fun search(keyword: String): List<Person> {
        viewModelScope.launch {
            val results = db.search(keyword)
            results.mapNotNull { result ->
                val uid = result.get("uid") as String
                val email = result.get("email") as String
                val displayName = result.get("displayName") as String
                listOfPersons.add(Person(uid, email, displayName))
                //println("AddPersonViewModel search(): $uid $email $displayName")
            }
        }
        return listOfPersons
    }

}