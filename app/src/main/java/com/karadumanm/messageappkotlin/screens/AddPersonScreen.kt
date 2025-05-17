package com.karadumanm.messageappkotlin.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.karadumanm.messageappkotlin.entities.Person
import com.karadumanm.messageappkotlin.ui.theme.designColor
import com.karadumanm.messageappkotlin.ui.theme.primaryColor
import com.karadumanm.messageappkotlin.viewmodel.AddPersonViewModel

@Composable
fun AddPersonScreen(viewModel: AddPersonViewModel){
    val displayName = remember { mutableStateOf("") }
    var personResult = remember { mutableStateOf<List<Person>?>(null) }

    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DisplayNameTextField(displayName = displayName.value, onValueChange = {displayName.value=it})
        Spacer(modifier = Modifier.padding(5.dp))
        SearchButton(viewModel)
    }

    if(personResult.value != null){


    }
}

@Composable
fun SearchButton(viewModel: AddPersonViewModel){
    Button(onClick = {viewModel.search()},
        colors = ButtonDefaults.buttonColors(contentColor = primaryColor, containerColor = designColor))
    {
        Text("Search")
    }
}

