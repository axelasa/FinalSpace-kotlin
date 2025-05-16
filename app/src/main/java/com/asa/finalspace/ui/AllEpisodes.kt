package com.asa.finalspace.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.asa.finalspace.model.episodes.GetAllEpisodesItem
import com.asa.finalspace.utill.ensureHttps
import com.asa.finalspace.viewmodel.AllEpisodesViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AllEpisodes(
    viewModel: AllEpisodesViewModel,
    modifier: Modifier,
    onEpisodeClick: (GetAllEpisodesItem) -> Unit
) {
    val episodeList by viewModel.episodeList.observeAsState(emptyList())
    val columns = 2

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title for the locations
        Text(
            text = "Final Space Episodes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        )  {
            items(episodeList){ episode ->
                GridItem(
                    episode = episode,
                    onClick = { onEpisodeClick(episode)}
                )
            }
        }

    }
}

@Composable
fun GridItem(episode:GetAllEpisodesItem,onClick:() -> Unit){
    val image = ensureHttps(episode.img_url)
    val chapter = episode.name


    Box(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(0.89f)
        .padding(8.dp)
        .clickable{ onClick() },

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            AsyncImage(
                model = image,
                contentDescription = chapter,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(230.dp)
                    .clip(shape = RoundedCornerShape(20)),
                placeholder = ColorPainter(Color.Gray),
                error = ColorPainter(Color.Red),
            )
            println("HERE IS THE IMAGE $image")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = chapter,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                softWrap = true
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}