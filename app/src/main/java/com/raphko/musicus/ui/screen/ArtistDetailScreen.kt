package com.raphko.musicus.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raphko.musicus.R
import com.raphko.musicus.ui.theme.MusicusTheme
import com.raphko.musicus.ui.viewmodel.ArtistViewModel

// Show the details of Artist with 1st album and its tracks
@Composable
fun ArtistDetailScreen(
    viewModel: ArtistViewModel = ArtistViewModel(), artistId: Long,
    navigateUp: (ArtistViewModel) -> Unit,
) {
    viewModel.loadArtistAndAlbum(artistId)

    Column() {
        ArtistHeader(viewModel, navigateUp)
        ArtistAlbum(viewModel)
    }
}

// Show up button, banner of the artist, custom app bar.
@Composable
private fun ArtistHeader(
    viewModel: ArtistViewModel,
    navigateUp: (ArtistViewModel) -> Unit
) {
    val artist by viewModel.artist.collectAsState()

    Box {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primaryVariant,
            elevation = 1.dp,
            contentColor = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(0.1f),
            ) {
                IconButton(onClick = { navigateUp(viewModel) }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Text(
                    text = artist.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (artist.pictureSmall != "https://e-cdns-images.dzcdn.net/images/artist//56x56-000000-80-0-0.jpg") {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(artist.pictureSmall)
                            .crossfade(true).build(),
                        contentDescription = "Photo of artist",
                        placeholder = painterResource(R.drawable.musicus_logo_transparent),
                        error = painterResource(R.drawable.musicus_logo_transparent),
                        modifier = Modifier
                            .size(40.dp),
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .size(40.dp),
                        painter = painterResource(R.drawable.musicus_logo_transparent),
                        contentDescription = "Logo"
                    )
                }
            }
        }
    }
}

// Section for Album and tracks
@Composable
private fun ArtistAlbum(viewModel: ArtistViewModel) {
    val artist by viewModel.artist.collectAsState()
    val album by viewModel.firstAlbum.collectAsState()
    val tracksLoaded by viewModel.tracksLoaded.collectAsState()
    val trackList by viewModel.trackList.collectAsState()
    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
        ) {
            if (artist.pictureBig != "https://e-cdns-images.dzcdn.net/images/artist//500x500-000000-80-0-0.jpg") {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(artist.pictureBig)
                        .crossfade(true).build(),
                    contentDescription = "Cover of artist",
                    contentScale = ContentScale.Crop,
                    alpha = 0.5f,
                    modifier = Modifier
                        .matchParentSize()
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.padding(0.dp, 15.dp)) {
                    if (album.coverMedium != "") {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(album.coverMedium)
                                .crossfade(true).build(),
                            contentDescription = "Photo of album",
                            placeholder = painterResource(R.drawable.musicus_logo_transparent),
                            error = painterResource(R.drawable.musicus_logo_transparent),
                            modifier = Modifier
                                .border(
                                    2.dp,
                                    MaterialTheme.colors.primary,
                                    RoundedCornerShape(15.dp)
                                )
                                .clip(RoundedCornerShape(15.dp))
                                .size(128.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.musicus_logo_transparent),
                            contentDescription = "Photo of album",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .border(
                                    2.dp,
                                    MaterialTheme.colors.primary,
                                    RoundedCornerShape(15.dp)
                                )
                                .clip(RoundedCornerShape(15.dp))
                                .size(128.dp)
                        )
                    }
                }
                Text(
                    text = album.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.padding(30.dp, 10.dp)
                )
            }
        }
    }
    // Wait until tracks are loaded to show them
    when {
        tracksLoaded -> TracklistUi(tracks = trackList)
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistDetailScreenPreview() {
    val viewModel = ArtistViewModel()
    val artistId: Long = 13
    viewModel.loadAlbumPreview()
    MusicusTheme {
        ArtistDetailScreen(viewModel, artistId, navigateUp = {})
    }
}