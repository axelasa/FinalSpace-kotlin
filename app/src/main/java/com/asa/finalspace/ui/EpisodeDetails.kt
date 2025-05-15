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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.asa.finalspace.utill.ensureHttps
import com.asa.finalspace.viewmodel.AllEpisodesViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EpisodeDetails(
    episodeId: Int,
    viewmodel: AllEpisodesViewModel,
    navController: NavController
){
    val episodeList by viewmodel.episodeList.observeAsState(emptyList())
    val episode = episodeList.find { it.id == episodeId }

    Scaffold {
        episode?.let { ep ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
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
                        Text(ep.name)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Episode image
                item {
                    AsyncImage(
                        model = ensureHttps(ep.img_url),
                        contentDescription = ep.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(horizontal = 18.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Episode title
                item {
                    Text(
                        ep.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 26.dp)
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                }

                // Episode details
                item {
                    EpisodeDetailItem(label = "Air Date:", value = ep.air_date)
                    EpisodeDetailItem(label = "Director", value = ep.director)
                    Spacer(modifier = Modifier.height(32.dp))

                    // Characters heading
                    Text(
                        text = "Characters",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 26.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Display character images
                items(ep.characters) { characterUrl ->
                    AsyncImage(
                        model = ensureHttps(characterUrl),
                        contentDescription = "Character",
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .padding(horizontal = 18.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Episode not found")
        }
    }
}
@Composable
fun EpisodeDetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
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