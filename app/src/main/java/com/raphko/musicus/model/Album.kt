package com.raphko.musicus.model

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("cover_medium")
    val coverMedium: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val tracks: TrackList
)