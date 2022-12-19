package com.pmesa48.marvelheroes.source.api.dto

import com.google.gson.annotations.SerializedName
import com.pmesa48.marvelheroes.source.api.dto.ThumbnailDto

data class EventDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailDto?
)
