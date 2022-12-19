package com.pmesa48.marvelheroes.source.api.dto

import com.google.gson.annotations.SerializedName

data class ServiceWrapper<T>(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    val data: Paginated<T>?
)

data class Paginated<T>(
    @SerializedName("offset")
    val offset : Int?,
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("results")
    val results: T?
)

data class CharacterDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailDto?,
    @SerializedName("description")
    val description: String?
)

data class ComicDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailDto?,
    @SerializedName("description")
    val description: String?
)

data class ThumbnailDto(
    @SerializedName("path")
    val path: String?,
    @SerializedName("extension")
    val extension: String?
)
