package com.pmesa48.marvelheroes.source.api.characters

import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper
import com.pmesa48.marvelheroes.source.api.common.ServerResponse
import com.pmesa48.marvelheroes.source.api.dto.CharacterDto

interface CharacterSource {

    suspend fun get(
        nameStartsWith: String,
        limit: Int,
        offset: Int
    ): ServerResponse<ServiceWrapper<List<CharacterDto>>>

    suspend fun getCharacterById(
        characterId: String
    ) : ServerResponse<ServiceWrapper<List<CharacterDto>>>
}
