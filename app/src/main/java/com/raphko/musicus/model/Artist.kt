package com.raphko.musicus.model

import kotlinx.serialization.*

@Serializable
data class Artist(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("picture_small")
    val pictureSmall: String,
    @SerialName("picture_medium")
    val pictureMedium: String,
    @SerialName("picture_big")
    val pictureBig: String
)