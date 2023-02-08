package com.raphko.musicus.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raphko.musicus.R
import com.raphko.musicus.ui.theme.MusicusTheme
import com.raphko.musicus.ui.viewmodel.ArtistListViewModel


@Composable
fun ArtistSearchScreen(viewModel: ArtistListViewModel, onNavigateToArtistDetail: (Long) -> Unit) {
    val focusRequester = remember { FocusRequester() }
    val artistsSearchList by viewModel.artistsSearchList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val launchedFirstSearch by viewModel.launchedFirstSearch.collectAsState()
    Column {
        SearchView(viewModel, focusRequester)
        Spacer(modifier = Modifier.height(4.dp))
        when {
            !launchedFirstSearch -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.musicus_logo_transparent),
                    contentDescription = "Name of artist",
                    modifier = Modifier
                        .clickable {
                            focusRequester.requestFocus()
                        }
                        .size(250.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Ask me, I'll find artists to music you !",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(30.dp, 0.dp)
                )
            }
            isLoading -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoaderUi()
            }
            else -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ArtistsSearchListUi(artistsSearchList = artistsSearchList, onNavigateToArtistDetail)
            }
        }
    }
}

@Composable
fun SearchView(
    viewModel: ArtistListViewModel,
    focusRequester: FocusRequester
) {
    val textState by viewModel.searchTerm.collectAsState()
    TextField(
        value = textState,
        onValueChange = { value ->
            viewModel.changeSearchTerm(value)
            if (textState.isNotEmpty()) {
                viewModel.searchArtists(textState)
            }
        },
        placeholder = {
            Text(color = Color.White, text = "Search an artist here !")
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(40.dp)
            )
        },
        trailingIcon = {
            if (textState != "") {
                IconButton(
                    onClick = {
                        viewModel.changeSearchTerm("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = MaterialTheme.colors.primaryVariant,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ArtistSearchScreenPreview() {
    val viewModel = ArtistListViewModel()
    viewModel.loadArtistsListPreview()
    MusicusTheme {
        ArtistSearchScreen(viewModel, onNavigateToArtistDetail = {})
    }
}