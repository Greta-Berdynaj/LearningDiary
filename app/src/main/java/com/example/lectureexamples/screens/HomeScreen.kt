package com.example.lectureexamples.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lectureexamples.R
import com.example.lectureexamples.models.Movie
import com.example.lectureexamples.models.getMovies

@Composable
fun HomeScreen(navController: NavController) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            AppTopBar(navController)
            Greeting()
            Text(
                style = MaterialTheme.typography.h6,
                text= "Movie List"
            )
            MyList(navController)
        }
        //MyList()
        //Greeting()
        //WelcomeText(modifier = Modifier.padding(16.dp), text = "welcome to my app!")
    }
}
//Done


@Preview
@Composable
fun MyList(navController: NavController = rememberNavController(),
           movies: List<Movie> = getMovies()){
    LazyColumn{
        items(movies) {movie ->
            MovieRow(
                movie = movie,
            )  { movieId ->
                Log.d("MyList", "item clicked $movieId")
                // navigate to detailscreen
                navController.navigate("detail/$movieId")
            }
        }
    }
}


@Composable
fun MovieRow(movie: Movie, onItemClick: (String) -> Unit = {}) {
    var showMoreInfo by remember {
        mutableStateOf(false)
    }
    // to display the movie info
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onItemClick(movie.id) },
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {
        // to hold the contents of the card
        Column {
            // Box composable display the movie poster
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            ) {
                // Image composable to display the movie poster
                Image(
                    painter = painterResource(id = R.drawable.avatar2), // load the poster image
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop
                )

                // Box composable to display the "Add to favorites" icon
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    // Icon composable to display the "Add to favorites" icon
                    Icon(
                        tint = MaterialTheme.colors.secondary,
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Add to favorites"
                    )
                }
            }
            // To display the movie title and expand/collapse icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // display the movie title
                Text(movie.title, style = MaterialTheme.typography.h6)
                // to display the expand/collapse icon
                Icon(
                    // choose the appropriate icon based on the state of showMoreInfo
                    imageVector = if (showMoreInfo) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "arrowUp",
                    // toggle the state of showMoreInfo when the icon is clicked
                    modifier = Modifier.clickable { showMoreInfo = !showMoreInfo })
            }
            // display the additional movie info
            Column(modifier = Modifier.fillMaxWidth()) {
                // to animate the visibility of the additional movie info
                AnimatedVisibility(visible = showMoreInfo) {
                    Column {
                        Text(text = "Director: ${movie.director}", style = MaterialTheme.typography.caption)
                        Text(text = "Released: ${movie.year}", style = MaterialTheme.typography.caption)
                        Text(text = "Genre: ${movie.genre}", style = MaterialTheme.typography.caption)
                        Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.caption)
                        Text(text = "Rating: ${movie.rating}", style = MaterialTheme.typography.caption)
                        Text(text = "Plot: ${movie.plot}", style = MaterialTheme.typography.caption)

                    }

                }
            }

        }
    }
}

@Preview
@Composable
fun WelcomeText(modifier: Modifier = Modifier, text: String = "default") {
    Row(
        modifier = modifier
            .padding(16.dp)
            .background(Color.Blue)
            .fillMaxWidth()
    ) {
        Text(modifier = modifier, text = "Hola")
        Text(text = text)
    }

}

@Preview
@Composable
fun Greeting() {
    Column(modifier = Modifier.padding(16.dp)) {
        var name by remember {
            mutableStateOf("")
        }

        Text(text = "Hello ${name}!")

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it},
            label = { Text("Name")}
        )


        /*
        // step 2 - add a mutableStateOf to fire the event for recomposition

       var name = mutableStateOf("")   // use a state holder to register changes
        // var name  by mutableStateOf("")
        Text(text = "Hello ${name.value}!")   // get value of state holder object

        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },    // change its value accordingly
            label = { Text("Name")}
        )
        */



        /*
        // step 3 - use remember
        var name by remember {         // use remember to skip overwriting after first composition
            mutableStateOf("")
        }

        Text(text = "Hello ${name}!")

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name")}
        )

         */
    }
}
@Composable
fun AppTopBar(navController: NavController) {

    var showMenu by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        title = { Text(text = "Movies") },
        actions = {

            // IconButton to open the menu
            IconButton(onClick = {
                showMenu = !showMenu
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Open Options"
                )
            }
            // drop down menu
            DropdownMenu(
                modifier = Modifier.width(width = 150.dp),
                expanded = showMenu,
                onDismissRequest = { showMenu=false}) {
                // Menu item to display user's favorites
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorite")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Favorites")
                }

            }
        })}