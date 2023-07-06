package com.example.marvelcharactersapp.view

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.marvelcharactersapp.Destination
import com.example.marvelcharactersapp.R

@Composable
fun CollectionsBottomNav(navController: NavHostController) {

        val navBackStackEntry = navController.currentBackStackEntryAsState()      // Tells the stack
        val currentDestination = navBackStackEntry.value?.destination        // Tells the current destination (Collections screen)

        val iconLibrary = painterResource(id = R.drawable.ic_library)
        val iconCollection = painterResource(id = R.drawable.ic_collection)

    NavigationBar(tonalElevation = 6.dp) {
            NavigationBarItem(
                icon = { iconLibrary },
                label = { Text(Destination.Library.route) },
                selected = currentDestination?.route == Destination.Library.route,
                onClick = { navController.navigate(Destination.Library.route){
                    popUpTo(Destination.Library.route)
                    launchSingleTop = true
                } }
            )
            NavigationBarItem(
                label = { Text(Destination.Collection.route) },
                icon = { iconCollection},
                selected = currentDestination?.route == Destination.Collection.route,
                onClick = { navController.navigate(Destination.Collection.route){
                    launchSingleTop = true
                } }
            )
    }

}