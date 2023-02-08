package com.raphko.musicus.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raphko.musicus.ui.viewmodel.ArtistListViewModel
import com.raphko.musicus.ui.viewmodel.ArtistViewModel

// Navigation controller
@Composable
fun MusicusNavigation() {
    val navController = rememberNavController()
    val actions = remember(navController) { AppActions(navController) }
    // We keep the viewModels in order to always have state after navigation
    val artistListViewModel = remember { ArtistListViewModel() }
    val artistViewModel = remember { ArtistViewModel() }
    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            artistViewModel.reset()
            ArtistSearchScreen(
                artistListViewModel,
                onNavigateToArtistDetail = actions.selectedArtist)
        }
        composable("artistDetail/{artistId}",
            arguments = listOf(
                navArgument("artistId") {
                    type = NavType.LongType
                })) {
                backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val artistId = arguments.getLong("artistId")
            ArtistDetailScreen(
                artistViewModel,
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