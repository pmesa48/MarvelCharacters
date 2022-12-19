package com.pmesa48.marvelheroes.repository.character

import com.pmesa48.marvelheroes.model.Character
import com.pmesa48.marvelheroes.model.Resource
import com.pmesa48.marvelheroes.source.api.characters.CharacterSource
import com.pmesa48.marvelheroes.source.api.dto.CharacterDto
import com.pmesa48.marvelheroes.source.cache.character.CharacterCache
import com.pmesa48.marvelheroes.repository.common.PaginatedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterRepository(
    private val source: CharacterSource,
    private val cache: CharacterCache,
    private val mapper: CharacterMapper
) : PaginatedRepository<CharacterDto, GetCharactersParam, Character>(cache) {

    override fun dtoToModel(dto: List<CharacterDto>) = mapper.map(dto)

    override suspend fun apiCall(param: GetCharactersParam) =
        source.get(param.query, cache.limit(), cache.size())

    fun getCharacterById(characterId: String) =
        flow {
            cache.get(characterId)?.let {
                emit(Resource.Found(it))
            } ?: emit(Resource.Error(""))
        }
}