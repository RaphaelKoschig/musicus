package com.raphko.musicus.model

import com.google.gson.annotations.SerializedName

data class AlbumList(
    @SerializedName("data")
    val albums: List<Album>
)