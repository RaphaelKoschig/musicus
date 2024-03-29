package com.raphko.musicus.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raphko.musicus.ui.theme.MusicusTheme
import com.raphko.musicus.ui.viewmodel.ArtistListViewModel
import com.raphko.musicus.ui.viewmodel.ArtistListViewModelAbstract
import com.raphko.musicus.ui.viewmodel.ArtistListViewModelPreview
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun ArtistSearchScreen(onNavigateToArtistDetail: (Long) -> Unit, viewModel: ArtistListViewModelAbstract) {
    val focusRequester = remember { FocusRequester() }
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val viewModel = viewModel
    Column {
        SearchView(viewModel, textState, focusRequester)
        Spacer(modifier = Modifier.height(4.dp))
        ArtistsSearchListContainer(state = textState, viewModel, onNavigateToArtistDetail)
    }
}

@Composable
fun SearchView(
    viewModel: ArtistListViewModelAbstract,
    state: MutableState<TextFieldValue>,
    focusRequester: FocusRequester
) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
            if (value.text.isNotEmpty()) {
                viewModel.searchArtists(value.text)
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
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value = TextFieldValue("")
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
    MusicusTheme {
        ArtistSearchScreen(onNavigateToArtistDetail = {},
            ArtistListViewModelPreview(
                isLoading = MutableStateFlow(false),
                launchedFirstSearch = MutableStateFlow(true),
                artistsSearchList = MutableStateFlow(ArtistListViewModel.artistSeachListPreview)
        ))
    }
}