package com.pmesa48.marvelheroes.source.api.characters

import com.pmesa48.marvelheroes.source.api.dto.CharacterDto
import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("/v1/public/characters")
    suspend fun filterByName(
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Response<ServiceWrapper<List<CharacterDto>>>

    @GET("/v1/public/characters")
    suspend fun all(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Response<ServiceWrapper<List<CharacterDto>>>

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: String
    ) : Response<ServiceWrapper<List<CharacterDto>>>
}
