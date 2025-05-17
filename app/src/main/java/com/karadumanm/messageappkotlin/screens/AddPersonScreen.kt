package com.karadumanm.messageappkotlin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.karadumanm.messageappkotlin.R
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

    val personList = personResult.value
    if(personList != null){
        Row {
            PersonListColumn(personList)
        }
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

@Composable
fun PersonListColumn(personList: List<Person>){
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
        items(personList.size) {
            PersonCardtoAdd(personList[it])
        }
    }
}

@Composable
fun PersonCardtoAdd(person: Person){
    Card(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(designColor)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier = Modifier.size(48.dp).clip(CircleShape),
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Person Image",
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.fillMaxWidth().padding(2.dp),
                horizontalAlignment = Alignment.Start)
            {
                Text(text = person.displayName)
                Text(text = person.email)
            }

            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Person Icon", tint = designColor)
            }
        }

    }
}

