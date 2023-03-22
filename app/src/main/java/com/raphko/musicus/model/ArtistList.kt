package com.raphko.musicus.model

import com.google.gson.annotations.SerializedName

data class ArtistList(
    @SerializedName("data")
    val artists: List<ArtistMinimal>
)