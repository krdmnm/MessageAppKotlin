package com.karadumanm.messageappkotlin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.karadumanm.messageappkotlin.R
import com.karadumanm.messageappkotlin.ui.theme.designColor
import com.karadumanm.messageappkotlin.viewmodel.MessagesViewModel

@Composable
fun MessagesScreen(viewModel: MessagesViewModel, navController: NavHostController, messages: List<String>){
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(5.dp)) {
        items(messages.size) {
            PersonCard()
        }
    }
}

@Composable
fun PersonCard(){
    Card (modifier = Modifier.fillMaxWidth().padding(5.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(designColor)
    ){
        Row (modifier = Modifier.fillMaxWidth().padding(2.dp),
            verticalAlignment = Alignment.CenterVertically){

            Image(modifier = Modifier.size(48.dp).clip(CircleShape),
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Person Image",
                contentScale = ContentScale.Crop)

            Text(text = "Person Name",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface)
        }
    }
}