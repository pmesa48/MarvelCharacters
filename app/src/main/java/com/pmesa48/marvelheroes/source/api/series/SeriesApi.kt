package com.pmesa48.marvelheroes.source.api.series

import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper
import com.pmesa48.marvelheroes.source.api.dto.SeriesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesApi {

    @GET("/v1/public/series")
    suspend fun get(
        @Query("characters") characterId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Response<ServiceWrapper<List<SeriesDto>>>
}
