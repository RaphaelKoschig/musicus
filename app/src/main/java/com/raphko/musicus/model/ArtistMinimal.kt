package com.raphko.musicus.model

import com.google.gson.annotations.SerializedName

data class ArtistMinimal(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture_medium")
    val pictureMedium: String
)