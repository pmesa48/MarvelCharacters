package com.pmesa48.marvelheroes.source.api.comics

import com.pmesa48.marvelheroes.source.api.dto.ComicDto
import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicApi {

    @GET("/v1/public/comics")
    suspend fun getComicsByCharacterId(
        @Query("characters") characterId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Response<ServiceWrapper<List<ComicDto>>>
}
