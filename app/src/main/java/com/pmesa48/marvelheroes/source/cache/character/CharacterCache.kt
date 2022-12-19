package com.pmesa48.marvelheroes.source.cache.character

import com.pmesa48.marvelheroes.model.Character
import com.pmesa48.marvelheroes.repository.character.GetCharactersParam
import com.pmesa48.marvelheroes.source.cache.common.PaginatedCache

interface CharacterCache : PaginatedCache<Character, GetCharactersParam> {
    suspend fun get(characterId: String): Character?
}