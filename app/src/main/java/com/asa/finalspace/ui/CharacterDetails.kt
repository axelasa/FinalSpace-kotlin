package com.asa.finalspace.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.asa.finalspace.viewmodel.AllCharactersViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharacterDetails(
    characterId: Int,
    viewmodel: AllCharactersViewModel,
    navController: NavController
) {
    val characterList by viewmodel.characterList.observeAsState(emptyList())
    val character = characterList.find { it.id == characterId }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(all = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Row {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Spacer(modifier = Modifier.width(50.dp))
                Text(character?.name ?: "Character Details")
            }
            Spacer(modifier = Modifier.height(20.dp))

            character?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PaddingValues(all = 18.dp))
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.Start
                ) {
                    AsyncImage(
                        model = it.img_url,
                        contentDescription = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        it.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    //CharacterDetailItem(label = "ID", value = it.id.toString())
                    CharacterDetailItem(label = "Status", value = it.status)
                    CharacterDetailItem(label = "Gender", value = it.gender)
                    CharacterDetailItem(label = "Origin", value = it.origin)
                    it.alias.let { alias ->
                        CharacterDetailItem(label = "Alias", value = alias.joinToString("\n"))
                    }

                    Spacer( modifier = Modifier.height(32.dp))
                }

            }?:
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(all = 18.dp),
                contentAlignment = Alignment.Center
            ){
                Text("Character not found")
            }
        }
    }
}

@Composable
fun CharacterDetailItem(
    label:String,
    value:String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )
    }
}
