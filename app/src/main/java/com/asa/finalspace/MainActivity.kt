package com.asa.finalspace

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asa.finalspace.network.HttpClient
import com.asa.finalspace.network.HttpClient.allCharactersService
import com.asa.finalspace.repository.AllCharactersRepositoryImpl
import com.asa.finalspace.ui.theme.FinalSpaceTheme
import com.asa.finalspace.viewmodel.AllCharactersViewModel
import com.asa.finalspace.viewmodel.ModelSate
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.asa.finalspace.model.characters.GetAllCharactersItem

class MainActivity : ComponentActivity() {
    lateinit var allCharactersViewModel: AllCharactersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        allCharactersViewModel = AllCharactersViewModel(
            application = application,
            allCharactersRepository = AllCharactersRepositoryImpl(allCharactersService = allCharactersService)
        )
        allCharactersViewModel.toastMessage.observe(this){
            println("HERE IS THE ERROR  $it")

            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        }
        setContent {
            FinalSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                    ShowData(
//                        viewModel = allCharactersViewModel,
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    CharactersGridView(
                        viewModel = allCharactersViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

@Composable
fun ShowData(viewModel: AllCharactersViewModel, modifier: Modifier = Modifier) {
    val characterList by viewModel.characterList.observeAsState(emptyList())
    println(
        "I have been called"
    )
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 14.dp, bottom = 14.dp, start = 14.dp)
    ) {
        //var columns =2
//        println(
//            "I have been called"
//        )
        items(characterList) { character ->
            println(
                "I have been called"
            )
            var name:String = ""
            var image:String = ""
            name = character.name
            image = character.img_url

//            println(
//                "HERE IS THE NAME $name"
//            )

//            Image(
//                painter = rememberAsyncImagePainter(image),
//                contentDescription = name,
//                modifier = Modifier.size(120.dp),
//                contentScale = ContentScale.Crop
//            )
//
//            Box(modifier = Modifier.size(10.dp))
//
//            Text( text = name )
//            Box(modifier = Modifier.size(10.dp))

        }
    }
}

@Composable
fun CharactersGridView(viewModel: AllCharactersViewModel,modifier: Modifier){
    val characterList by viewModel.characterList.observeAsState(emptyList())
    val columns =2

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(characterList) { character ->
            GridItem(character = character)

            }

    }
}

@Composable
fun GridItem( character: GetAllCharactersItem){
    //val characterList by character.characterList.observeAsState(emptyList())

    var image:String = character.img_url
    var name:String = character.name

//    for (c in characterList){
//        name = c.name
//        image = c.img_url
//
//    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f),
        elevation = CardDefaults.cardElevation(4.dp)
        ) {
        Column (modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            )
        {
            AsyncImage(
                model = image,
                contentDescription = name,
                modifier = Modifier.size(80.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = name)
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