package com.pmesa48.marvelheroes.source.api.characters

import com.pmesa48.marvelheroes.source.api.common.GetHeroesListException
import com.pmesa48.marvelheroes.source.api.common.ServiceException
import com.pmesa48.marvelheroes.source.api.common.BaseService

class CharacterSourceImpl(
    private val charactersApi : CharactersApi
) : BaseService(), CharacterSource {

    override suspend fun get(nameStartsWith: String, limit: Int, offset: Int) =
        safeCall {
            if(nameStartsWith.isNotEmpty())
                charactersApi.filterByName(nameStartsWith, limit, offset)
            else
                charactersApi.all(limit, offset)
        }

    override suspend fun getCharacterById(characterId: String) =
        safeCall { charactersApi.getCharacterById(characterId) }

}