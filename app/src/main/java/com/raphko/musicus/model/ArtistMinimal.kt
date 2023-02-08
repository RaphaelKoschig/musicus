package com.raphko.musicus.model

import kotlinx.serialization.*

@Serializable
data class ArtistMinimal(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("picture_medium")
    val pictureMedium: String
)