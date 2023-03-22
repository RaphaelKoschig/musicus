package com.raphko.musicus.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raphko.musicus.ui.viewmodel.ArtistViewModel

// Navigation controller
@Composable
fun MusicusNavigation() {
    val navController = rememberNavController()
    val actions = remember(navController) { AppActions(navController) }
    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            ArtistSearchScreen(
                onNavigateToArtistDetail = actions.selectedArtist
            )
        }
        composable(
            "artistDetail/{artistId}",
            arguments = listOf(
                navArgument("artistId") {
                    type = NavType.LongType
                })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val artistId = arguments.getLong("artistId")
            ArtistDetailScreen(
                artistId = artistId,
                navigateUp = actions.navigateUp
            )
        }
    }
}

// Here we have differents actions of navigation in the app
private class AppActions(
    navController: NavHostController
) {
    val selectedArtist: (Long) -> Unit = { artistId: Long ->
        navController.navigate("artistDetail/$artistId")
    }
    val navigateUp: (ArtistViewModel) -> Unit = { artistViewModel: ArtistViewModel ->
        navController.navigateUp()
    }
}