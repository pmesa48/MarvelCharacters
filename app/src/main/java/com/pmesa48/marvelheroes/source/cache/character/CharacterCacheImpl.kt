package com.pmesa48.marvelheroes.source.cache.character

import com.pmesa48.marvelheroes.model.Character
import com.pmesa48.marvelheroes.repository.character.GetCharactersParam
import com.pmesa48.marvelheroes.source.cache.common.InMemoryPaginatedCache
import javax.inject.Inject

class CharacterCacheImpl @Inject constructor() :
    CharacterCache, InMemoryPaginatedCache<Character, GetCharactersParam>() {

    override fun threshold() = THRESHOLD

    override fun limit() = LIMIT

    override suspend fun get(characterId: String): Character? = data.find { it.id == characterId }

    companion object {
        private const val LIMIT = 50
        private const val THRESHOLD = 10
    }
}