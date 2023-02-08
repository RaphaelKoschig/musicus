package com.raphko.musicus.model

import kotlinx.serialization.*

@Serializable
data class Album(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("cover_medium")
    val coverMedium: String,
    @SerialName("release_date")
    val releaseDate: String,
    var tracks: List<Track> = arrayListOf()
)