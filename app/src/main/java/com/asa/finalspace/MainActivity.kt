package com.asa.finalspace

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.asa.finalspace.viewmodel.AllCharactersViewModel
import coil.compose.AsyncImage
import com.asa.finalspace.model.characters.GetAllCharactersItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.asa.finalspace.network.HttpClient.allEpisodesService
import com.asa.finalspace.repository.AllEpisodesRepositoryImpl
import com.asa.finalspace.routes.NavItems
import com.asa.finalspace.routes.Routes
import com.asa.finalspace.ui.AllEpisodes
import com.asa.finalspace.ui.AllLocations
import com.asa.finalspace.ui.CharacterDetails
import com.asa.finalspace.ui.EpisodeDetails
import com.asa.finalspace.viewmodel.AllEpisodesViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    //private lateinit var allCharactersViewModel: AllCharactersViewModel

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        allCharactersViewModel = AllCharactersViewModel(
//            application = application,
//            allCharactersRepository = AllCharactersRepositoryImpl(allCharactersService = allCharactersService)
//        )
//        allCharactersViewModel.toastMessage.observe(this) {
//            println("HERE IS THE ERROR  $it")
//
//            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        //}

        setContent {
            AppNavigation()
        }

        /*setContent {
            FinalSpaceTheme {
             FinalSpaceApplication {
                 Scaffold(modifier = Modifier.fillMaxSize()) {
                     AppNavigation()
                 }
             }

            }
        }*/
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.CHARACTER_LIST,
            modifier = Modifier.padding(contentPadding)  // Apply padding here
        ) {
            composable(Routes.CHARACTER_LIST) {
                val viewModel: AllCharactersViewModel = koinViewModel()
                val toastMessage by viewModel.toastMessage.collectAsState()
                LaunchedEffect(toastMessage) {
                    toastMessage?.let {
                        Toast.makeText(context,it,Toast.LENGTH_LONG).show()
                    }
                }
                CharactersGridView(
                    viewModel = viewModel,
                    modifier = Modifier.padding(all = 8.dp),
                    onCharacterClick = { character ->
                        navController.navigate(Routes.characterDetailsRoute(character.id))
                    }
                )
            }
            composable(route = Routes.EPISODES_SCREEN) {
//                val episodesViewModel = remember {
//                    AllEpisodesViewModel(
//                        application = Application(),
//                        allEpisodesRepository = AllEpisodesRepositoryImpl(allEpisodesService = allEpisodesService)
//                    )
//                }
                AllEpisodes(
                    modifier = Modifier.fillMaxSize(),
                    onEpisodeClick = { episode ->
                        navController.navigate(Routes.episodeDetailsRoute(episode.id))
                    }
                )
            }
            composable(route = Routes.ALL_LOCATIONS) {
//                val locationsViewModel = remember {
//                    AllLocationsViewModel(
//                        application = Application(),
//                        allLocationsRepository = AllLocationsRepositoryImpl(allLocationService = allLocationsService)                    )
//                }
                AllLocations(
                    modifier = Modifier.fillMaxSize(),
                    onLocationClick = { location ->
                        navController.navigate(Routes.locationDetailsRoute(location.id))
                    }
                )
            }
            composable(
                route = Routes.CHARACTER_DETAILS,
                arguments = Routes.characterDetailsArguments
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
                CharacterDetails(
                    characterId = characterId,
                    navController = navController,
                )
            }
            composable(
                route = Routes.EPISODE_DETAILS,
                arguments = Routes.episodeDetailsArguments
            )
            { backStackEntry ->
                val episodeId = backStackEntry.arguments?.getInt("episodeId") ?: 0
                val episodesViewModel = remember {
                    AllEpisodesViewModel(
                        application = Application(),
                        allEpisodesRepository = AllEpisodesRepositoryImpl(allEpisodesService = allEpisodesService)
                    )
                }
                EpisodeDetails(
                    episodeId = episodeId,
                    viewmodel = episodesViewModel,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        NavItems(
            title = "HOME",
            icon = Icons.Default.Face,
            route = Routes.CHARACTER_LIST
        ),
        NavItems(
            title = "EPISODES",
            icon = Icons.Default.PlayArrow,
            route = Routes.EPISODES_SCREEN
        ),
        NavItems(
            title = "LOCATIONS",
            icon = Icons.Default.LocationOn,
            route = Routes.ALL_LOCATIONS
        ),
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { (Icon(item.icon, contentDescription = item.title)) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

        }
    }
}

@Composable
fun CharactersGridView(
    viewModel: AllCharactersViewModel= koinViewModel(),
    modifier: Modifier,
    onCharacterClick: (GetAllCharactersItem) -> Unit
) {
    val characterList by viewModel.characterList.collectAsState(emptyList())
    val columns = 2

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(characterList) { character ->
            GridItem(
                character = character,
                onClick = { onCharacterClick(character) }
            )

        }

    }
}

@Composable
fun GridItem(
    character: GetAllCharactersItem, onClick: () -> Unit
) {
    val image = character.img_url
    val name = character.name

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)// Slightly taller cards
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
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
                contentDescription = name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(130.dp)
                    .clip(RoundedCornerShape(50)), // Circular image
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                softWrap = true
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    FinalSpaceTheme {
//        Greeting("Android")
//    }
//}