package com.raphko.musicus.model

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("preview")
    val preview: String
)