package com.pmesa48.marvelheroes.source.api.events

import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper
import com.pmesa48.marvelheroes.source.api.dto.EventDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsApi {

    @GET("/v1/public/events")
    suspend fun get(
        @Query("characters") characterId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Response<ServiceWrapper<List<EventDto>>>
}
