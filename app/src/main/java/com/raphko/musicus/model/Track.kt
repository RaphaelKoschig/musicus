package com.raphko.musicus.model

import kotlinx.serialization.*

@Serializable
data class Track(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("preview")
    val preview: String
)