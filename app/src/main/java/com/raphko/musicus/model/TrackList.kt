package com.raphko.musicus.model

import com.google.gson.annotations.SerializedName

data class TrackList(
    @SerializedName("data")
    val tracks: List<Track>
)