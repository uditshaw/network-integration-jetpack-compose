package com.example.marvelcharactersapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marvelcharactersapp.ui.theme.MarvelCharactersAppTheme
import com.example.marvelcharactersapp.view.CollectionsBottomNav
import com.example.marvelcharactersapp.view.CollectionsScreen
import com.example.marvelcharactersapp.view.LibraryScreen
import com.example.marvelcharactersapp.viewmodel.LibraryApiViewModel
import com.google.gson.internal.GsonBuildConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

sealed class Destination(val route: String) {
    object Library : Destination("Library")
    object Collection : Destination("Collection")
    object CharacterDetail : Destination("Character/{characterId}") {
        fun createRoute(characterId: Int?) = "character/$characterId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val libraryVM by viewModels<LibraryApiViewModel>()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelCharactersAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()  // Remembers navigation controller
                    CharacterScaffold(navController = navController, libraryVM)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun CharacterScaffold(navController: NavHostController, libraryVM: LibraryApiViewModel) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }, bottomBar = {
        CollectionsBottomNav(
            navController = navController
        )
    }) { paddingValues ->
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                LibraryScreen(navController = navController, paddingValues = paddingValues, vm = libraryVM )
            }
            composable(Destination.Collection.route) {
                CollectionsScreen()
            }
            composable(Destination.CharacterDetail.route) {

            }
        }
    }


}
