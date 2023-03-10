package com.raphko.musicus.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raphko.musicus.R
import com.raphko.musicus.model.ArtistMinimal
import com.raphko.musicus.ui.theme.MusicusTheme
import com.raphko.musicus.ui.viewmodel.ArtistListViewModel

// List of artists after a search is made
@Composable
fun ArtistsSearchListUi(
    artistsSearchList: List<ArtistMinimal>,
    onNavigateToArtistDetail: (Long) -> Unit
) {
    if (!artistsSearchList.isEmpty()) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            items(artistsSearchList.chunked(3)) { row ->
                Row(
                    modifier = Modifier.padding(0.dp, 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    row.forEach { artist ->
                        ArtistCard(artist = artist, onNavigateToArtistDetail)
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No result.",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.padding(30.dp, 0.dp)
            )
        }
    }
}

// Card to show artist picture and name
@Composable
private fun ArtistCard(
    artist: ArtistMinimal,
    onNavigateToArtistDetail: (Long) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = screenWidth / 3 - 20.dp
    Card(
        modifier = Modifier
            .clickable { onNavigateToArtistDetail(artist.id) }
            .width(cardWidth)
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(4.dp),
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (artist.pictureMedium != "https://e-cdns-images.dzcdn.net/images/artist//250x250-000000-80-0-0.jpg") {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(artist.pictureMedium)
                        .crossfade(true).build(),
                    contentDescription = "Photo of artist",
                    placeholder = painterResource(R.drawable.musicus_logo_transparent),
                    error = painterResource(R.drawable.musicus_logo_transparent),
                    contentScale = ContentScale.Fit
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.musicus_logo_transparent),
                    contentDescription = "Logo"
                )
            }
            Text(
                modifier = Modifier.padding(10.dp),
                text = artist.name,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(color = Color.LightGray, fontSize = 14.sp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewArtistsSearchListUi() {
    MusicusTheme() {
        ArtistsSearchListUi(
            ArtistListViewModel.artistSeachListPreview,
            onNavigateToArtistDetail = {}
        )
    }
}